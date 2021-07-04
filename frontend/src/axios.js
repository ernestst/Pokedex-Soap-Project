import axios from 'axios'
import Vue from 'vue';

axios.defaults.baseURL = `https://tomcat.misern.pl:443/app/`
axios.defaults.headers = {'Content-Type': 'text/xml'}

axios.interceptors.request.use(config => {
/*    config.data = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pok="http://pokedex.ws.wi.pb.edu.pl/">' +
        '<soapenv:Header/>' +
        '<soapenv:Body>' +
        config.data +
        '</soapenv:Body>' +
        '</soapenv:Envelope>';*/
    return config;
})

axios.interceptors.response.use((response) => {
    //return response.request.responseXML.children;
    const vm = new Vue();
    return vm.$x2js.xml2js(response.data).Envelope.Body
})

export default axios;