/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProxyConfig {

    /**
     * Конфигурирование bean-a для проксирования URL выгрузки данных аудита
     *
     * @param exportUrl url-а сервиса выгрузки
     * @return bean
     */
    @Bean
    public ServletRegistrationBean<ProxyServlet> proxyExportServlet(@Value("${audit.export.url}") String exportUrl) {
        ServletRegistrationBean<ProxyServlet> bean = new ServletRegistrationBean<>(new ProxyServlet(), "/audit/export/*");
        Map<String, String> params = new HashMap<>();
        params.put("targetUri", exportUrl);
        params.put(ProxyServlet.P_LOG, "true");
        bean.setInitParameters(params);
        bean.setName("proxyExportServlet");
        return bean;
    }

}
