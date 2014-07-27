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

    void "test fromUrl basic valid url"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/")
        then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
    void "test fromUrl ip, port & GET params"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/v1/asdkjh/asd/asd/asd?wehrkwh=1q124")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
    void "test fromUrl invalid URL: no api version"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1:5000/")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
    void "test fromUrl with no port (ip)"() {
        when:
        def registry = Registry.fromUrl("http://172.17.42.1/v1/")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
    void "test fromUrl with no port (hostname)"() {
        when:
        def registry = Registry.fromUrl("http://production/v1/")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
    void "test fromUrl with username and password"() {
        when:
        def registry = Registry.fromUrl("http://username:password@172.17.42.1:5000/v1/")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }

    void "test fromUrl with username only url"() {
        when:
        def registry = Registry.fromUrl("http://username@172.17.42.1:5000/v1/")
                then:
        registry != null
        registry.url != null
        registry.url.length() > 0
        registry.apiVersion != null
        registry.apiVersion.equals("v1")
    }
}
