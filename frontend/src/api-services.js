import axios from './axios.js';

class ApiServices {
    static beforeData = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pok="http://pokedex.ws.wi.pb.edu.pl/"><soapenv:Header/><soapenv:Body>'
    static beforeDataAuth = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:auth="http://auth.ws.wi.pb.edu.pl/"><soapenv:Header/><soapenv:Body>'
    static afterData = '</soapenv:Body></soapenv:Envelope>'

    static home = (data) => axios.post(`home`, ApiServices.beforeData + '<pok:getPokemonById><dex>' + data + '</dex></pok:getPokemonById>' + ApiServices.afterData);

    static getPokemonById = (data) => axios.post('pokedex', ApiServices.beforeData + '<pok:getPokemonById><dex>' + data + '</dex></pok:getPokemonById>' + ApiServices.afterData);

    static getPokemons = (page) => axios.post('pokedex', ApiServices.beforeData + '<pok:getPokemons><page>' + page +'</page></pok:getPokemons>' + ApiServices.afterData);

    static getPokemonImageByCode = (data) => axios.post('pokedex/images', ApiServices.beforeData + '<pok:downloadImage><code>' + data + '</code></pok:downloadImage>' + ApiServices.afterData);

    static getPokemonEvolutions = (data) => axios.post('pokedex/evolutions', ApiServices.beforeData + '<pok:getPokemonEvolutions><dex>' + data + '</dex></pok:getPokemonEvolutions>' + ApiServices.afterData);

    static getPokemonWithNameAndTypes = (types, name, page) => axios.post('pokedex', ApiServices.beforeData +
        '<pok:getPokemonsAdvanced>' +
        '<page>' + page + '</page>' +
        '<type>' + types + '</type>' +
        '<name>' + name + '</name>' +
        '</pok:getPokemonsAdvanced>' + ApiServices.afterData);

    static getPokemonWithName = (name, page) => axios.post('pokedex', ApiServices.beforeData +
        '<pok:getPokemonsByName>' +
        '<page>' + page + '</page>' +
        '<name>' + name + '</name>' +
        '</pok:getPokemonsByName>' + ApiServices.afterData);

    static login = (login, password) => axios.post('user', ApiServices.beforeDataAuth + '<auth:login><credentials><login>' + login + '</login><password>' + password + '</password></credentials></auth:login>'
        + ApiServices.afterData);

    static register = (login, password) => axios.post('user', ApiServices.beforeDataAuth + '<auth:register><credentials><login>' + login + '</login><password>' + password + '</password></credentials></auth:register>'
        + ApiServices.afterData);


    static addPokemon = (name, code, nationalDex, primaryType, secondType) => axios.post('pokedex', ApiServices.beforeData + '<pok:savePokemon>' +
        '<pokemon>' +
        '<code>' + code + '</code>' +
        '<name>' + name + '</name>' +
        '<nationalDex>' + nationalDex + '</nationalDex>' +
        '<primaryType>' + primaryType + '</primaryType>' +
        '<secondType>' + secondType + '</secondType>' +
        '</pokemon>' +
        '</pok:savePokemon>' + ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login'),
                'Content-Type': 'text/xml'
            }
    });

    static addPokemonImage = (image, code) => axios.post('pokedex/images', ApiServices.beforeData +
        '<pok:uploadImage>' +
        '<image>' + image + '</image>' +
        '<code>' + code + '</code>' +
        '</pok:uploadImage>' +
        ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login')
            }
    });

    static addPokemonEvolution = (additional, evolutionNationalDex, item, level, sourceNationalDex) => axios.post('pokedex/evolutions', ApiServices.beforeData +
    '<pok:savePokemonEvolution>\n' +
        '<evolutionDetails>\n' +
        '<additional>' + additional + '</additional>' +
        '<evolutionNationalDex>' + evolutionNationalDex + '</evolutionNationalDex>' +
        '<item>' + item +'</item>' +
        '<level>' + level +'</level>' +
        '<sourceNationalDex>' + sourceNationalDex + '</sourceNationalDex>' +
        '</evolutionDetails>' +
        '</pok:savePokemonEvolution>' + ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login'),
                'Content-Type': 'text/xml'
            }
    })

    static addUserTeamMember = (dex) => axios.post('user/team', ApiServices.beforeDataAuth +
        '<auth:addUserTeamMember>' +
        '<login>' + localStorage.getItem('login')  +'</login>' +
        '<dex>' + dex + '</dex>' +
        '</auth:addUserTeamMember>' + ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login'),
                'Content-Type': 'text/xml'
            }
    });

    static getUserTeamMember = () => axios.post('user/team', ApiServices.beforeDataAuth +
        '<auth:getUserTeamMembers>' +
        '<login>' + localStorage.getItem('login')  +'</login>' +
        '</auth:getUserTeamMembers>' + ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login'),
                'Content-Type': 'text/xml'
            }
    });

    static removeUserTeamMember = (dex) => axios.post('user/team', ApiServices.beforeDataAuth +
        '<auth:removeUserTeamMember>' +
        '<login>' + localStorage.getItem('login')  +'</login>' +
        '<dex>' + dex + '</dex>' +
        '</auth:removeUserTeamMember>' + ApiServices.afterData, {
        "headers":
            {
                'password': localStorage.getItem('password'),
                'username': localStorage.getItem('login'),
                'Content-Type': 'text/xml'
            }
    });
}

export default ApiServices;