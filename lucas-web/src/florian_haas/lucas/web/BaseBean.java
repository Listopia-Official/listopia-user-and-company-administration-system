package florian_haas.lucas.web;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.*;

import florian_haas.lucas.model.ReadOnlyEntity;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.exporter.CustomCSVExporter;
import florian_haas.lucas.web.util.WebUtils;

public abstract class BaseBean<T extends ReadOnlyEntity> implements Serializable {

	private static final long serialVersionUID = -5947754241791290184L;

	private final String baseName;
	private final String beanName;
	private final String screenName;
	private final Class<T> entityClass;

	protected List<T> searchResults = new ArrayList<>();

	protected List<T> selectedEntities = new ArrayList<>();

	private List<Boolean> resultsDatatableColumns = new ArrayList<>();

	private Boolean paginatorActive = Boolean.TRUE;

	protected BaseBean(String baseName, Integer maxColumnCount) {
		this.baseName = baseName;
		beanName = baseName.concat("Bean");
		screenName = baseName.concat("Screen");
		entityClass = Utils.getClassFromArgs(this.getClass());
		for (int i = 0; i < maxColumnCount; i++) {
			resultsDatatableColumns.add(Boolean.TRUE);
		}
	}

	@PostConstruct
	public void onPostConstruct() {
		WebUtils.executeTask((params) -> {
			ViewNavigationBean bean = WebUtils.getCDIManagedBean(ViewNavigationBean.class);
			List<Long> ids = bean.getIds(getViewID(this.baseName));
			if (ids != null) {
				for (Long id : ids) {
					T entity = entityGetter(id);
					if (!searchResults.contains(entity)) this.searchResults.add(entity);
				}
			}
			params.add(searchResults.size());
			return searchResults.size() > 0;
		}, Boolean.TRUE, Boolean.FALSE, "lucas.application.dataTemplate.init.message");
	}

	protected final String getViewID(String baseName) {
		return "lucas:view_" + baseName;
	}

	protected void navigateToBeanSingle(String baseName, Function<T, Long> idGetter) {
		navigateToBean(baseName, (entity) -> {
			Long id = idGetter.apply(entity);
			return id == null ? null : Arrays.asList(id);
		});
	}

	protected void navigateToBean(String baseName, Function<T, List<Long>> idGetter) {
		ViewNavigationBean bean = WebUtils.getCDIManagedBean(ViewNavigationBean.class);
		List<Long> ids = new ArrayList<>();
		for (T entity : selectedEntities) {
			List<Long> ids2 = idGetter.apply(entity);
			if (ids2 != null) ids.addAll(ids2);
		}
		bean.setIds(getViewID(baseName), ids);
	}

	public String getBaseName() {
		return this.baseName;
	}

	public String getBeanName() {
		return beanName;
	}

	public String getScreenName() {
		return this.screenName;
	}

	public List<T> getSearchResults() {
		return this.searchResults;
	}

	public List<T> getSelectedEntities() {
		return this.selectedEntities;
	}

	public void setSelectedEntities(List<T> selectedEntities) {
		this.selectedEntities = selectedEntities;
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
	}

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public final void search() {
		WebUtils.executeTask((params) -> {
			List<? extends T> results = this.searchEntities();
			searchResults.clear();
			selectedEntities.clear();
			searchResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application." + screenName + ".search.message");
	}

	public final void refresh() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(entityClass, searchResults, selectedEntities, this::entityGetter, false);
			params.add(searchResults.size());
			return true;
		}, "lucas.application." + screenName + ".refresh.message");
	}

	public void activatePaginator() {
		paginatorActive = Boolean.TRUE;
	}

	public void deactivatePaginator() {
		paginatorActive = Boolean.FALSE;
	}

	public boolean isPaginatorActive() {
		return paginatorActive;
	}

	public abstract EnumPermission getFindDynamicPermission();

	public abstract EnumPermission getPrintPermission();

	public abstract EnumPermission getExportPermission();

	protected abstract List<? extends T> searchEntities();

	protected abstract T entityGetter(Long entityId);

	/*
	 * -------------------- Export Data Dialog Start --------------------
	 */

	@NotBlank
	private String exportDataDialogDelim = ";";

	@NotBlank
	private String exportDataDialogFilename = null;

	@NotNull
	private Boolean exportDataDialogExportSelectionOnly = Boolean.FALSE;

	@NotNull
	private Boolean exportDataDialogGenerateHeader = Boolean.TRUE;

	public String getExportDataDialogDelim() {
		return exportDataDialogDelim;
	}

	public void setExportDataDialogDelim(String exportDataDialogDelim) {
		this.exportDataDialogDelim = exportDataDialogDelim;
	}

	public String getExportDataDialogFilename() {
		return exportDataDialogFilename;
	}

	public void setExportDataDialogFilename(String exportDataDialogFilename) {
		this.exportDataDialogFilename = exportDataDialogFilename;
	}

	public Boolean getExportDataDialogExportSelectionOnly() {
		return exportDataDialogExportSelectionOnly;
	}

	public void setExportDataDialogExportSelectionOnly(Boolean exportDataDialogExportSelectionOnly) {
		this.exportDataDialogExportSelectionOnly = exportDataDialogExportSelectionOnly;
	}

	public Boolean getExportDataDialogGenerateHeader() {
		return this.exportDataDialogGenerateHeader;
	}

	public void setExportDataDialogGenerateHeader(Boolean exportDataDialogGenerateHeader) {
		this.exportDataDialogGenerateHeader = exportDataDialogGenerateHeader;
	}

	public StreamedContent getExportDataDialogDownload() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		CustomCSVExporter exporter = new CustomCSVExporter(exportDataDialogDelim);
		return exporter.export(context,
				(DataTable) context.getViewRoot()
						.findComponent("dataAccorditionPanel:" + baseName + "SearchResultsForm:" + baseName + "SearchResultsTable"),
				exportDataDialogExportSelectionOnly, exportDataDialogGenerateHeader, exportDataDialogFilename);
	}

	public void initExportDataDialog() {
		exportDataDialogDelim = ";";
		exportDataDialogFilename = null;
		exportDataDialogExportSelectionOnly = Boolean.FALSE;
		exportDataDialogGenerateHeader = Boolean.TRUE;
	}

	/*
	 * -------------------- Export Data Dialog End --------------------
	 */

}
