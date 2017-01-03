Selenium Example Learn
======================
An example project that is cloned from the Selenium Template, but with Learn 9.1 specific examples: 

* The internal Selenium Core and Requirements Coverage modules
* Selenium WebDriver, JUnit, and Hamcrest

### How to create a new project from the scaffold
1. Create a new directory on your local machine for the project (if you don't already have one).
2. Clone this scaffold into that new directory.
3. In a bash (OS X) or gitbash (Windows) window, change directory (cd) into the cloned scaffold.
4. Run the following bash command: `$ rm -rf .git` (This trashes the git information stored that is only relevant for the scaffold)
5. Open up the scaffold's **pom.xml** file in any text editor (I'd recommend against using IntelliJ at this point). 
6. At the top of the file, you'll see the `<artifactId>` property. Change this from the name of the scaffold to the name of the new project (and save).
7. At this point, you can also change the name of the directory (still bearing the name of the scaffold) to match the name of the new project.
8. Launch IntelliJ and select "Import Project" (you may need to select "Maven" here). 
9. Locate the new directory created in step 1, the renamed directory therein from step 7, and the **pom.xml** file.
10. Complete the import process (double-checking that group and artifact IDs are accurate for your project).
11. Initialize git in IntelliJ (VCS menu -> Import into Version Control -> Create Git Repository -> select the project directory).
12. Run the following bash command to prepare the initial commit: `$ git add .`
13. Run the following bash command to make the first commit: `$ git commit -m "Initial commit; base project"`
14. Follow the instructions in the BitBucket repository to set remote and push everything up.

### Running Selenium Standalone Server
The selenium standalone server must be running in order to run tests locally.

1. Download selenium standalone server https://goo.gl/Lyo36k
2. Open a bash or command prompt window and run the following command to start the selenium server
3. Run the command in the folder where you downloaded the selenium server jar: java -jar selenium-server-standalone-3.0.1.jar

### Setting up your configuration file
In _src/main/resources_, you should see a file named _example-test-config.xml_. This demonstrates all of the recognized properties that can be set. Note that "config.xml" is already added to _.gitignore_; feel free to use IntelliJ's refactor feature to change the name of the test config file to that. Whatever the new name (or location) may be, be sure to update the `CONFIG_FILE` constant in BaseTest (and register it with _.gitignore_).

#### Config Sections/Properties

##### project-name (Required)
A short name for the app and version being tested (e.g. Portfolio v3.1).

##### home-page-url (Required)
The base url for the homepage of the app being tested.

##### runLocation (Required)
* local
* sauce (run at your own risk; better stability planned for the next selenium core version).

##### api-information (optional)
* Use this only when you are integrating with the API(s) of the app.
    * scheme - http or https
    * host-name - the root domain name for the api

##### allure (optional)
* These two properties set the behavior of the screenshot and html attachments for the allure report. The default if not set is 'false' for both.

##### browsers (At least 1 browser configuration is required)
Any number of browser configurations can be specified here. The composition of these browser configs is as follows:

    <browser>
        <name> REQUIRED - safari, chrome, ie, iPhone (mobile), or android (mobile) </name>
        <version> REQUIRED - browser version (mobile version if iPhone or android specified for name </version>
        <os> only needed for desktop browsers on Sauce (defaults to any available) - Windows, OS X </os>
        <os-version> only needed for desktop browsers on Sauce (defaults to any available) - Mavericks, 10, etc. </os-version>
        <device> only needed if iPhone or android specified for name</device>
    </browser>

##### sauce (Optional -- only needed if running on Sauce)
These are the sauce-specific properties (including your own username and authentication-id). These can be reviewed on Sauce's website in the "Optional Sauce Testing Features" section: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options

### Developing your tests
You'll find abstract base classes in both _src/main/app/pages_ and _src/test/app_. These should serve as the parent classes for your page objects and tests, respectively. There is an example page object (navigating to Google's homepage) and corresponding test that demonstrate how classes could be written.

#### A quick note on random Strings
The dependency for Apache's RandomStringUtils (https://commons.apache.org/proper/commons-lang/javadocs/api-3.4/org/apache/commons/lang3/RandomStringUtils.html) is already included. If you need a random alphanumeric String that is 10 characters long, for example, you would simply do the following:

`String randomString = RandomStringUtils.randomAlphanumeric(10);`

#### Page Objects
This scaffold assumes the page object model with Selenium's abstract LoadableComponent class. Whenever you create a new page object class and extend BasePage, IntelliJ will remind you (via a red underline) to implement the `load()` and `isLoaded()` methods in addition to creating a constructor that matches the superclass.

#### Test Classes
There are some additional comments in BaseTest itself describing what is happening therein, but you'll notice that it also includes common `setUp()` and `tearDown()` methods. These can be Overwritten normally in derivative test classes, but it would likely behoove you in most situations to still make the call (at least) to `super.setUp()` if you do.

##### In each test
* Requirements Coverage File
    * In the example test class (GoogleExampleTest), you'll see a `.writeToFile()` call for the Requirements Coverage file. You'll notice a corresponding line in _src/main/resources/requirements_coverage.txt_. This call should be made in every test to send that information over to the coverage file (note that sorting should be automatic, and duplicate lines won't be written).
* Allure annotations
    * `@Features` and `@Stories` should be included for each test method as well.
 
### Running your tests
So long as your config file is in place, you should be able to run a test (or tests) directly in IntelliJ or via maven's cli (`$ mvn clean test`). If you'd like to overwrite any of the properties set in your config file, simply include them as a property in the maven cli call (for example: **runLocation** in my .xml config is "local", but I'd like for it to be "sauce" at runtime. Instead of modifying the xml, I could run the tests with the following command: `$ mvn clean test -DrunLocation=sauce`).

#### Parallel runs
You'll notice that BaseTest is set up to use JUnit's Parameterized runner. This is what makes it possible to specify multiple browser configurations. Parallel runs can still be achieved via maven cli using two properties:

* -DparallelTarget
    * This defaults to classes. With the Parameterized runner, each browser configuration is considered its own class. Leaving this target as the default, then, means that one test will run at a time, but your different browser configurations will run simultaneously for that test.
    * another option here is to set the target to methods, which will run all the methods in a single test class simultaneously for a single browser configuration (then it'll either go to the next class for that same configuration or the next configuration in the same test class).
* -DparallelCount
    * This specifies the maximum number of parallel threads. Note that this doesn't necessarily mean that you will always have the max number available running at once. This is especially important to prevent using more than our available simultaneous VMs on Sauce (in addition to ensuring that you don't overload your local machine if running locally).

### Generating Allure Reports
After running the desired tests via maven's cli, simply run `$ mvn site`. The generated report will be in _target/site/allure-maven-plugin.html_. You will need to open it up in a browser to view the report (in intelliJ: right-click on the file -> Open In Browser).