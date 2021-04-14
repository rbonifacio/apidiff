This is a slight extension of the APIDiff library.
The main goal of this extension is to allow a researcher
to compute the breaking changes between two revisions.
The following test case explains how to use this
additional feature.

```java
@Test
public void testMethodBreakingChanges() {
  try {
    String r1rv60 = "52b0902592e770b8116f80f2eab7a4048b589d7d"; // commit id for revision r1rv60
    String r1rv59 = "6de1c17dda8ffdb19431ffcadbce1836867a27a9"; // commit id for revision r1rv59
            
    String out = getClass().getResource("/").getFile();
            
    APIDiff diff = new APIDiff("bc", "https://github.com/bcgit/bc-java.git");
            
    diff.setPath(out);
            
    Result res = diff.detectChangeBetweenRevisions(r1rv60, r1rv59, Classifier.API);
            
    long methodBreakingChanges = res.getChangeMethod().stream()
                                         .filter(c -> c.isBreakingChange())
                                         .count();
            
    Assert.assertEquals(40, methodBreakingChanges); // expecting 40 methodBreakingChanges
            
  }
  catch(Exception ex) {
     Assert.fail();
  }
}
```


   * Note: this is a contribution of Francisco Handrick da Costa
   
# APIDiff (original README file) 

A tool to identify API breaking and non-breaking changes between two versions of a Java library. APIDiff analyses libraries hosted on the distributed version control system _git_.

## Contributors
The project has been maintained by [Aline Brito](https://github.com/alinebrito) and [Laerte Xavier](https://github.com/xavierlaerte) under supervision of Professor [Marco Tulio Valente](https://github.com/mtov) ([Aserg](http://aserg.labsoft.dcc.ufmg.br/) [UFMG](https://www.ufmg.br/)) and Professor [Andre Hora](https://github.com/andrehora) ([UFMS](https://www.ufms.br/) [FACOM](https://www.facom.ufms.br/)).

## Catalog

The following _Breaking Changes_ (BC) are supported: 

| Element  | Breaking Changes (BC) |
| ------------- | ------------- |
| Type  | Rename Type, Move Type, Move and Rename Type, Remove Type, Lost Visibility, Add Final Modifier,  Remove Static Modifier, Change in Supertype, Remove Supertype |
| Method  | Move Method, Rename Method, Remove Method, Push Down Method, Inline Method, Change in Parameter List, Change in Exception List, Change in Return Type Method, Lost Visibility, Add Final Modifier, Remove Static Modifier  | 
| Field  |  Remove Field, Move Field, Push Down Field, Change in Default Value, Change in Type Field,  Lost Visibility, Add Final Modifier | 

The following _Non-breaking Changes_ (NBC) are supported: 

| Element  | Non-breaking Changes (NBC) |
| ------------- | ------------- |
| Type  | Add Type, Extract Supertype, Gain Visibility, Remove Final Modifier, Add Static Modifier, Add Supertype, Deprecated Type |
| Method  | Pull Up Method, Gain Visibility, Remove Final Modifier, Add Static Modifier, Deprecated Method, Add Method, Extract Method| 
| Field  | Pull Up Field, Add Field, Deprecated Field, Gain Visibility, Remove Final Modifier|


The refactorings catalog is reused from [RefDiff](https://github.com/aserg-ufmg/RefDiff).

## Examples

* Detecting changes in version histories:

```java
APIDiff diff = new APIDiff("bumptech/glide", "https://github.com/bumptech/glide.git");
diff.setPath("/home/projects/github");

Result result = diff.detectChangeAllHistory("master", Classifier.API);
for(Change changeMethod : result.getChangeMethod()){
    System.out.println("\n" + changeMethod.getCategory().getDisplayName() + " - " + changeMethod.getDescription());
}
```
* Detecting changes in specific commit:

```java
APIDiff diff = new APIDiff("mockito/mockito", "https://github.com/mockito/mockito.git");
diff.setPath("/home/projects/github");

Result result = diff.detectChangeAtCommit("4ad5fdc14ca4b979155d10dcea0182c82380aefa", Classifier.API);
for(Change changeMethod : result.getChangeMethod()){
    System.out.println("\n" + changeMethod.getCategory().getDisplayName() + " - " + changeMethod.getDescription());
}
```
* Fetching new commits:

```java
APIDiff diff = new APIDiff("bumptech/glide", "https://github.com/bumptech/glide.git");
diff.setPath("/home/projects/github");
    
Result result = diff.fetchAndDetectChange(Classifier.API);
for(Change changeMethod : result.getChangeMethod()){
    System.out.println("\n" + changeMethod.getCategory().getDisplayName() + " - " + changeMethod.getDescription());
}
```

* Writing a CSV file:

```java
APIDiff diff = new APIDiff("mockito/mockito", "https://github.com/mockito/mockito.git");
diff.setPath("/home/projects/github");
Result result = diff.detectChangeAtCommit("4ad5fdc14ca4b979155d10dcea0182c82380aefa", Classifier.API);
		
List<String> listChanges = new ArrayList<String>();
listChanges.add("Category;isDeprecated;containsJavadoc");
for(Change changeMethod : result.getChangeMethod()){
    String change = changeMethod.getCategory().getDisplayName() + ";" + changeMethod.isDeprecated()  + ";" + changeMethod.containsJavadoc() ;
    listChanges.add(change);
}
UtilFile.writeFile("output.csv", listChanges);
```

* Filtering Packages according to their names:

```java 
Classifier.INTERNAL: Elements that are in packages with the term "internal".

Classifier.TEST: Elements that are in packages with the terms "test"|"tests", or is in source file "src/test", or ends with "test.java"|"tests.java".

Classifier.EXAMPLE: Elements that are in packages with the terms "example"|"examples"|"sample"|"samples"|"demo"|"demos"

Classifier.EXPERIMENTAL: Elements that are in packages with the term "experimental".

Classifier.NON_API: Internal, test, example or experimental elements.

Classifier.API: Elements that are not non-APIs.
``` 

## Usage

APIDiff is available in the [Maven Central Repository](https://mvnrepository.com/artifact/com.github.aserg-ufmg/apidiff/2.0.0):

```xml
<dependency>
    <groupId>com.github.aserg-ufmg</groupId>
    <artifactId>apidiff</artifactId>
    <version>2.0.0</version>
</dependency>
```
## Publications

Aline Brito, Laerte Xavier, Andre Hora, Marco Tulio Valente. [APIDiff: Detecting API Breaking Changes](http://homepages.dcc.ufmg.br/~mtov/pub/2018-saner-apidiff.pdf). In 25th International Conference on Software Analysis, Evolution and Reengineering (SANER), Tool Track, pages 1-5, 2018.

Learn more about our research group at http://aserg.labsoft.dcc.ufmg.br/
