package alexsoroka.util

import spock.lang.Specification

class AssertSpec extends Specification {
  def 'nonNull should throw exception when object is null'() {
    when:
    Assert.nonNull(null, 'bla')

    then:
    def exception = thrown(IllegalArgumentException.class)
    exception.message == 'bla'
  }

  def 'nonNull should not throw exception when object is not null'() {
    when:
    Assert.nonNull(new Object(), 'bla')

    then:
    noExceptionThrown()
  }

  def 'isTrue should throw exception when expression is false'() {
    when:
    Assert.isTrue(1 == 2, 'bla')

    then:
    def exception = thrown(IllegalArgumentException.class)
    exception.message == 'bla'
  }

  def 'isTrue should not throw exception when expression is true'() {
    when:
    Assert.isTrue(1 == 1, 'bla')

    then:
    noExceptionThrown()
  }

  def 'state should throw illegal state exception when state expression is false'() {
    when:
    Assert.state(1 == 2, 'bla')

    then:
    def exception = thrown(IllegalStateException.class)
    exception.message == 'bla'
  }

  def 'state should not throw exception when state expression is true'() {
    when:
    Assert.state(1 == 1, 'bla')

    then:
    noExceptionThrown()
  }
}
