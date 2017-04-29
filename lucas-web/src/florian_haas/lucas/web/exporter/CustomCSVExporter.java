package florian_haas.lucas.web.exporter;

import java.io.*;
import java.nio.charset.Charset;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.*;
import org.primefaces.model.*;

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
}
