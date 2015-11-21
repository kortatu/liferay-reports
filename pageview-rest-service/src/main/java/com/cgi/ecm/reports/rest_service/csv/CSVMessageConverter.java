package com.cgi.ecm.reports.rest_service.csv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a List of PortletReportLine to a CSVFile.
 * This Converter is not generic and used only for this kind of report.
 * Also, it doesn't return the content as a file but as a text output
 * Created by kortatu on 11/08/15.
 */
public class CSVMessageConverter extends AbstractHttpMessageConverter<List<PortletReportLine>> {

    private static final Log log = LogFactory.getLog(CSVMessageConverter.class);

    //tag::header[]
    private final String[] header = new String[] { "applicationId","nodeId","viewTimestamp", "companyId", "userId" , "userEmail", "session", "pageId", "pageName", "processTime",
                                            "portletId", "portletSetup", "portletAdditionalConfig"};
    //end::header[]

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(List.class) && isCsv(mediaType);
    }

    private boolean isCsv(MediaType mediaType) {
        return mediaType.getSubtype().equals("csv");
    }

//tag::canWrite[]
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        boolean canWrite = List.class.isAssignableFrom(clazz) && isCsv(mediaType);
        if (canWrite)
            log.info("We can convert to CSV. MediaType: ["+mediaType.toString()+"]");
        return canWrite;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(supportedMediaType());
        return list;
    }

    private MediaType supportedMediaType() {
        return new MediaType("text","csv");
    }
//end::canWrite[]

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected List<PortletReportLine> readInternal(Class<? extends List<PortletReportLine>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    //tag::write[]
    @Override
    protected void writeInternal(List<PortletReportLine> portletReportLines, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(supportedMediaType());
        log.info("Converting to csv ["+portletReportLines.size()+"] report lines");
        OutputStream body = outputMessage.getBody();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(body);
        CsvPreference preferences = CsvPreference.STANDARD_PREFERENCE;
        CsvBeanWriter csvBeanWriter = new CsvBeanWriter(outputStreamWriter, preferences);
        csvBeanWriter.writeHeader(header);
        for (PortletReportLine line : portletReportLines) {
            csvBeanWriter.write(line, header);
        }
        body.flush();
        body.close();
    }
//end::write[]

}
