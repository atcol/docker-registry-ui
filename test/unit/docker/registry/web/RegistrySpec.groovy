package docker.registry.web

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
        def url = Registry.fromUrl("http://172.17.42.1:5000/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
    }

    void "test toUrl with auth no password"() {
        when:
        def url = Registry.fromUrl("http://alex@172.17.42.1:5000/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
        "alex".equals(url.toURI().userInfo)
    }

    void "test toUrl with auth username & password"() {
        when:
        def url = Registry.fromUrl("http://alex:password@172.17.42.1:5000/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        5000 == url.toURL().port
        "/v1".equals(url.toURL().path)
        "alex:password".equals(url.toURI().userInfo)
    }

    void "test toUrl port is default when no port specified"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        -1 == url.toURL().port // Registry.fromUrl parses -1, the default port when none provided, to 80, for simplicity
        "/v1".equals(url.toURL().path)
    }

    void "test toUrl with explicit port 80 is forgotton once converted to java.util.URL"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1:80/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        -1 == url.toURL().port
        "/v1".equals(url.toURL().path)
    }

    void "test toUrl with Port resolution when the user does not enter a port, defaulting to 0"() {
        when:
        def url = Registry.fromUrl("http://172.17.42.1:0/v1/").toUrl()
        then:
        url != null
        "172.17.42.1".equals(url.toURL().host)
        // url.toURL to the Java implementation of URL class
        -1 == url.toURL().port
        "/v1".equals(url.toURL().path)
    }

    void "test fromUrl basic valid url"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/")
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
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/asdkjh/asd/asd/asd?wehrkwh=1q124")
        then:
        registry == null
    }

    void "test fromUrl invalid URL: no api version"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5001/")
        then:
        registry == null
    }

    void "test fromUrl with no port (ip)"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1/v1/")
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
        def registry = Registry.fromUrl("http://production/v1/")
        then:
        registry != null
        "production".equals(registry.host)
        registry.username == null
        registry.password == null
        "v1".equals(registry.apiVersion)
    }

    void "test fromUrl with username and password"() {
        when:
        def registry = Registry.fromUrl("http://username:password@172.17.42.1:5000/v1/")
        then:
        registry != null
        "172.17.42.1".equals(registry.host)
        "v1".equals(registry.apiVersion)
        "username".equals(registry.username)
        "password".equals(registry.password)
    }

    void "test fromUrl with username only url"() {
        when:
        def registry = Registry.fromUrl("http://username@172.17.42.1:5000/v1/")
        then:
        registry != null
        "172.17.42.1".equals(registry.host)
        "v1".equals(registry.apiVersion)
        "username".equals(registry.username)
        registry.password == null
    }
}
