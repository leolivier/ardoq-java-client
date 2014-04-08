ardoq-java-client
=================

Small Java wrapper for the [Ardoq](http://ardoq.com) REST-api.

###Install
Add the ardoq maven repository to your pom.xml
```xml
    <repositories>
        <repository>
            <id>ardoq.com</id>
            <url>http://maven.ardoq.com/snapshot/</url>
        </repository>
    </repositories>
```

Add `ardoq-java-client` to your dependencies

```xml
    <dependencies>
        ...
        <dependency>
            <groupId>com.ardoq.api</groupId>
            <artifactId>client</artifactId>
            <version>0.5.0</version>
        </dependency>
        ...
    </dependencies>
```
###Usage
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password");
```
The client will operate on the default organization (Personal). To change this
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password").setOrganization("my-organization");
```
###Starting a small project
```java
ArdoqClient client = new ArdoqClient(host, ardoqUsername, ardoqPassword);
Model model = client.model().getModelByName("Application service");
Workspace workspace = client.workspace().createWorkspace(new Workspace("demo-workspace", model.getId(), "Description"));
ComponentService componentService = client.component();

Component webshop = componentService.createComponent(new Component("Webshop", workspace.getId(), "Webshop description"));
Component webShopCreateOrder = componentService.createComponent(new Component("createOrder", workspace.getId(), "Order from cart", webshop.getId()));

Component erp = componentService.createComponent(new Component("ERP", workspace.getId(), ""));
Component erpCreateOrder = componentService.createComponent(new Component("createOrder", workspace.getId(), "", erp.getId()));
//Create a Synchronous integration between the Webshop:createOrder and ERP:createOrder services
Reference createOrderRef = new Reference(workspace.getId(), "Order from cart", webShopCreateOrder.getId(), erpCreateOrder.getId(), model.getReferenceTypeByName("Synchronous"));
createOrderRef.setReturnValue("Created order");
Reference reference = client.reference().createReference(createOrderRef);

List<String> componentIds = Arrays.asList(webShopCreateOrder.getId(), erpCreateOrder.getId());
List<String> referenceIds = Arrays.asList(reference.getId());
client.tag().createTag(new Tag("Customer", workspace.getId(), "", componentIds, referenceIds));
```

Running this simple example let's Ardoq visualize the components and theire relationships.


####Models
The model API is not stable yet, so you have to create your Model in the UI and refer to the id.
####More examples
The api is pretty straight forward. For more examples, please refer to the [tests](https://github.com/ardoq/ardoq-java-client/tree/master/src/test/java/com/ardoq/service).
