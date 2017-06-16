package florian_haas.lucas.web.exporter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.component.*;
import javax.faces.component.html.*;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.component.celleditor.CellEditor;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.*;
import org.primefaces.model.*;

import florian_haas.lucas.web.converter.LongTextConverter;
import florian_haas.lucas.web.util.WebUtils;

public class CustomCSVExporter extends CSVExporter {

	private String delim = ";";

	public CustomCSVExporter(String delim) {
		this.delim = delim;
	}

	public StreamedContent export(FacesContext context, DataTable table, boolean selectionOnly, boolean generateHeader, String filename)
			throws IOException {
		return this.exportExtended(context, table, filename, false, generateHeader, selectionOnly, "UTF-8", null, null, null);
	}

	public StreamedContent exportExtended(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean generateHeader,
			boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, ExporterOptions options)
			throws IOException {
		StringWriter out = new StringWriter();
		Writer writer = new PrintWriter(out);

		if (generateHeader) addColumnFacets(writer, table, Exporter.ColumnType.HEADER);

		if (pageOnly) {
			exportPageOnly(context, table, writer);
		} else if (selectionOnly) {
			exportSelectionOnly(context, table, writer);
		} else {
			exportAll(context, table, writer);
		}

		if (generateHeader && table.hasFooterColumn()) {
			addColumnFacets(writer, table, Exporter.ColumnType.FOOTER);
		}
		writer.flush();
		writer.close();

		return new DefaultStreamedContent(new ByteArrayInputStream(out.toString().replaceAll(",", delim).getBytes(Charset.forName(encodingType))),
				WebUtils.CSV_MIME, filename.concat("." + WebUtils.CSV_TYPE.toLowerCase()));
	}

	@SuppressWarnings({
			"rawtypes", "unchecked" })
	protected String exportValue(FacesContext context, UIComponent component) {
		if ((component instanceof HtmlCommandLink)) {
			HtmlCommandLink link = (HtmlCommandLink) component;
			Object value = link.getValue();

			if (value != null) return String.valueOf(value);

			for (UIComponent child : link.getChildren()) {
				if ((child instanceof ValueHolder)) return exportValue(context, child);
			}

			return "";
		}

		if ((component instanceof ValueHolder)) {
			if ((component instanceof EditableValueHolder)) {
				Object submittedValue = ((EditableValueHolder) component).getSubmittedValue();
				if (submittedValue != null) return submittedValue.toString();
			}

			ValueHolder valueHolder = (ValueHolder) component;
			Object value = valueHolder.getValue();

			Converter converter = valueHolder.getConverter();
			if (converter == null && value != null) {
				Class valueType = value.getClass();
				converter = context.getApplication().createConverter(valueType);
			}

			if (converter != null && value != null) {
				if ((component instanceof UISelectMany)) {
					StringBuilder builder = new StringBuilder();
					List collection = null;

					if ((value instanceof List)) {
						collection = (List) value;
					} else if (value.getClass().isArray()) {
						collection = Arrays.asList(new Object[] {
								value });
					} else {
						throw new FacesException("Value of " + component.getClientId(context) + " must be a List or an Array.");
					}

					int collectionSize = collection.size();
					for (int i = 0; i < collectionSize; i++) {
						Object object = collection.get(i);
						builder.append(converter.getAsString(context, component, object));

						if (i < collectionSize - 1) {
							builder.append(",");
						}
					}

					String valuesAsString = builder.toString();
					builder.setLength(0);

					return valuesAsString;
				}

			}

			return converter == null ? value != null ? value.toString() : ""
					: (value != null && converter instanceof LongTextConverter) ? value.toString() : converter.getAsString(context, component, value);
		}

		if ((component instanceof CellEditor)) return exportValue(context, ((CellEditor) component).getFacet("output"));
		if ((component instanceof HtmlGraphicImage)) return (String) component.getAttributes().get("alt");

		return "";
	}
}
