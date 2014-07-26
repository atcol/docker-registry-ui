class DwrConfigBootStrap {

    def init = { servletContext -> }
    def destroy = {}

    def dwrconfig = {
        service(name: 'registryService', javascript: 'RestService') {
            exclude('setMetaClass,getMetaClass,setProperty,getProperty')
        }

        create(creator: 'new', javascript: 'AddressLookup') {
            param(name: 'class') { 'uk.ltd.getahead.dwrdemo.address.AddressLookup' }
        }
    }
}

