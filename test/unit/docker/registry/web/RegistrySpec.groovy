package docker.registry.web

import docker.registry.web.support.RegistryAction
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Registry)
class RegistrySpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test toUrl without auth"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
    }

    void "test toUrl with auth no password"() {
        when:
        def url = Registry.fromUrl("http://alex@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
        "alex".equals(url.toURI().userInfo)
    }

    void "test toUrl with auth username & password"() {
        when:
        def url = Registry.fromUrl("http://alex:password@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
        "alex:password".equals(url.toURI().userInfo)
    }

    void "test toUrl port is default when no port specified"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        -1 == url.toURL().port // Registry.fromUrl parses -1, the default port when none provided, to 80, for simplicity
        "/v1".equals(url.toURL().path)
    }

    void "test toUrl with explicit port 80 is forgotten once converted to java.util.URL"() {
        when:
        def urlStr = Registry.fromUrl("http://172.17.42.1:80/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        urlStr != null
        def url = urlStr.toURL()
        "172.17.42.1" == url.host
        -1 == url.port
        "/v1" == url.path
    }

    void "test toUrl with Port resolution when the user does not enter a port, defaulting to 0"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1:0/v1/").orElseThrow { new AssertionError("No registry instance") }.toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        // url.toURL to the Java implementation of URL class
        -1 == url.toURL().port
        "/v1".equals(url.toURL().path)
    }

    void "test fromUrl basic valid url"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }
        then:
        registry != null
        registry.host != null
        "172.17.42.1".equals(registry.host)
        registry.port == 5000
        registry.username == null
        registry.password == null
        "v1".equals(registry.apiVersion)
    }

    void "test fromUrl invalid path yields null"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/asdkjh/asd/asd/asd?wehrkwh=1q124").isPresent()
        then:
        !registry
    }

    void "test fromUrl invalid URL: no api version"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5001/").isPresent()
        then:
        !registry
    }

    void "test fromUrl with no port (ip)"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1/v1/").orElseThrow() { new AssertionError("No registry instance") }
        then:
        registry != null
        "172.17.42.1".equals(registry.host)
        registry.port == 80
        registry.username == null
        registry.password == null
        "v1".equals(registry.apiVersion)
    }

    void "test fromUrl with no port (hostname)"() {
        when:
        def registry = Registry.fromUrl("http://production/v1/").orElseThrow { new AssertionError("No registry instance") }
        then:
        registry != null
        "production".equals(registry.host)
        registry.username == null
        registry.password == null
        "v1".equals(registry.apiVersion)
    }

    void "test fromUrl with username and password"() {
        when:
        def registry = Registry.fromUrl("http://username:password@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }
        then:
        registry != null
        "172.17.42.1".equals(registry.host)
        "v1".equals(registry.apiVersion)
        "username".equals(registry.username)
        "password".equals(registry.password)
    }

    void "test fromUrl with username only url"() {
        when:
        def registry = Registry.fromUrl("http://username@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }
        then:
        registry != null
        "172.17.42.1".equals(registry.host)
        "v1".equals(registry.apiVersion)
        "username".equals(registry.username)
        registry.password == null
    }

    void "test toString doesn't NPE on null password :)"() {
        when:
        def registry = Registry.fromUrl("http://admin@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }
        registry.password = null
        then:
        "Registry{id=null, protocol='http', host='172.17.42.1', port=5000, apiVersion='v1', username='admin', password='null', repositoryService=null, version=null}"
                .equals(registry.toString())
    }

    void "test toString doesn't contain password :)"() {
        when:
        def registryStr = Registry.fromUrl("http://admin:abc123@172.17.42.1:5000/v1/").orElseThrow { new AssertionError("No registry instance") }.toString()
        then:
        registryStr != null
        !registryStr.contains("abc123")
        registryStr.contains("******") // * replaces abc123
    }

    void "toUrl with action PING - registry v1"() {
        when:
        def registry = Registry.fromUrl("http://localhost:5000/v1/").orElseThrow { new AssertionError("No registry instance") }
        def actionUrl = registry.toUrl(RegistryAction.PING)
        then:
        actionUrl != null
        actionUrl.getPort() == 5000
        actionUrl.getHost() == "localhost"
        actionUrl.getPath() == "/v1/_ping"
    }

    void "toUrl with action PING - registry v2"() {
        when:
        def registry = Registry.fromUrl("http://localhost:5000/v2/").orElseThrow { new AssertionError("No registry instance") }
        def actionUrl = registry.toUrl(RegistryAction.PING)
        then:
        actionUrl != null
        actionUrl.getPort() == 5000
        actionUrl.getHost() == "localhost"
        actionUrl.getPath() == "/v2/_catalog"
    }
}
