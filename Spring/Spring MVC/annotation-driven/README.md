# annotation-driven이 하는 일

`<mvc:annotation-driven />`을 사용하면 MVC 관련 많은 설정이 자동으로 진행됩니다. 편리하지만 잘못 사용시 기존 Spring 설정이 변경될 수 있기 때문에, 해당 설정이 어떤 설정을 진행하는지 알고 사용해야 합니다.

* @RequestMapping, @ExceptionHandler가 사용된 Controller에서 request를 처리하기 위해 `RequestMappingHandlerAdapter, RequestMappingHandlerMapping, ExceptionResolver`를 bean으로 등록

> 여담이지만, 전자정부 프레임워크를 사용시 Controller에서 Parameter를 Map으로 받기위해 @CommandMap을 사용하는 부분이 있었는데 <mvc:annotation-driven /> 사용시  @CommandMap을 사용할 수 없는 문제가 있음에도 불구하고 아무것도 모른채 <mvc:annotation-driven />를 사용함에 따라 파일다운로드가 안됬던 문제가 있었습니다. 그래서 사용시 유의하셔야 합니다.

* DataBinding에 사용되는 PropertyEditors 외에 `ConversionService` instance를 사용한 변환
* ConversionService를 통해 `@NumberFormat` 어노테이션을 사용해서 **Number Field formatting 지원**
  * ``@DateTimeFormat`` 어노테이션을 사용해서 **Date, Calendar, Long, Joda Time Field formatting 지원**
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