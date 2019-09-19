package ru.i_novus.ms.audit.filter;

import org.apache.commons.io.input.BoundedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Обертка запроса для сохранения данных полученных через getInputStream() и возможности повторного его вызова
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestWrapper.class);
    // Ограничение на размер полученных данных через getInputStream
    private static final Integer MAX_BYTE_SIZE = 1_048_576; // 1 MB
    private StringBuilder body;

    RequestWrapper(HttpServletRequest request) {
        super(request);
        body = new StringBuilder();
        try (
                InputStream bounded = new BoundedInputStream(request.getInputStream(), MAX_BYTE_SIZE);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bounded))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.toString().getBytes());
        return new ServletInputStream() {
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do nothing
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
