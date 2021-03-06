package functional.tests

import geb.spock.GebSpec
import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import grails.web.http.HttpHeaders
import spock.lang.Issue

@Integration
class EmbeddedSpec extends GebSpec {

    void "Test render can handle a domain with an embedded src/groovy class"() {
        given:"A rest client"
        def builder = new RestBuilder()

        when:
        def resp = builder.get("${baseUrl}embedded")

        then:"The response is correct"
        resp.status == 200
        resp.headers.getFirst(HttpHeaders.CONTENT_TYPE) == 'application/json;charset=UTF-8'

        resp.text == '{"id":1,"customClass":{"name":"Bar"},"inSameFile":{"text":"FooBar"},"name":"Foo"}'
    }

    void "Test jsonapi render can handle a domain with an embedded src/groovy class"() {
        given:"A rest client"
        def builder = new RestBuilder()

        when:
        def resp = builder.get("${baseUrl}embedded/jsonapi")

        then:"The response is correct"
        resp.status == 200
        resp.headers.getFirst(HttpHeaders.CONTENT_TYPE) == 'application/json;charset=UTF-8'

        resp.text == '{"data":{"type":"embedded","id":"2","attributes":{"customClass":{"name":"Bar2"},"inSameFile":{"text":"FooBar2"},"name":"Foo2"}},"links":{"self":"/embedded/show/2"}}'
    }

    @Issue("https://github.com/grails/grails-views/issues/171")
    void 'test render can handle a domain with an embedded and includes src/groovy class'() {
        given: 'a rest client'
        def builder = new RestBuilder()

        when:
        def resp = builder.get("${baseUrl}embedded/embeddedWithIncludes")

        then: 'the response is correct'
        resp.status == 200
        resp.headers.getFirst(HttpHeaders.CONTENT_TYPE) == 'application/json;charset=UTF-8'

        resp.text == '{"customClass":{"name":"Bar3"},"name":"Foo3"}'
    }

    @Issue("https://github.com/grails/grails-views/issues/171")
    void 'Test jsonapi render can handle a domain with an embedded and includes src/groovy class'() {
        given: 'a rest client'
        def builder = new RestBuilder()

        when:
        def resp = builder.get("${baseUrl}embedded/embeddedWithIncludesJsonapi")

        then: 'the response is correct'
        resp.status == 200
        resp.headers.getFirst(HttpHeaders.CONTENT_TYPE) == 'application/json;charset=UTF-8'

        resp.text == '{"data":{"type":"embedded","id":"4","attributes":{"customClass":{"name":"Bar4"},"name":"Foo4"}},"links":{"self":"/embedded/show/4"}}'
    }


}
