# annotation-driven이 하는 일

annotation-driven을 Spring의 많은 설정들이 자동으로 진행됩니다.

프로젝트 초기에 해당 설정을 사용한다면 문제가 없겠지만, 기존 프로젝트에 annotation-driven 사용시 기존 설정이 덮여 쓰기 되는 경우가 발생할 수 있기 때문에 사용시 유의해야 합니다.

annotation-driven이 하는 설정은 아래와 같습니다.

> 참고한 공식 문서의 버전은 3.2.x 입니다. 

* @RequestMapping, @ExceptionHandler가 사용된 Controller에서 request를 처리하기 위해 `RequestMappingHandlerAdapter, RequestMappingHandlerMapping, ExceptionResolver`를 bean으로 등록

* DataBinding에 사용되는 PropertyEditors 외에 `ConversionService` instance를 사용한 데이터 바인딩
* ConversionService를 통해 `@NumberFormat` 어노테이션을 사용해서 **Number Field formatting 지원**
* ``@DateTimeFormat`` 어노테이션을 사용해서 **Date, Calendar, Long, Joda Time, Field formatting 지원**
  > JDK 8 이상일시 LocalDate, LocalDateTime도 지원합니다.
* ClassPath에 JSR-303 Provider가 존재하면 **@Controller에서 입력을 검증**하기 위한 ``@Valid`` 지원
  * JSR-303은 Bean Validation 스펙
* @RequestBody 메서드 매개 변수 및 @ResponseBody가 붙어있는 @Requestmapping 또는 @ExceptionHandler 메서드의 return 값에 ``HttpMessageConverter`` 지원
  * annotation-driven으로 인해 활성화 되는 HttpMessageConverters
    * ByteArrayHttpMessageConverter
    * StringHttpMessageConverter
    * ResourceHttpMessageConverter
    * SourceHttpMessageConverter
    * FormHttpMessageConverter
    * Jaxb2RootElementHttpMessageConverter
      * classpath에 JAXB2가 존재 해야 Bean으로 등록
    * MappingJackson2HttpMessageConverter or (MappingJacksonHttpMessageConverter)
      * classpath에 Jackckson2 or (Jackson)가 존재해야 Bean으로 등록
    * AtomFeedHttpMessageConverter
      * classpath에 Rome가 존재 해야 Bean으로 등록
    * RssChannelHttpMessageConverte
      * classpath에 Rome가 존재 해야 Bean으로 등록
* 참고
  * [17. Web MVC framework](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html)
