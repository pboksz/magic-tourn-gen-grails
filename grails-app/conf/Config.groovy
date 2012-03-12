// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
      xml: ['text/xml', 'application/xml'],
      text: 'text/plain',
      js: 'text/javascript',
      rss: 'application/rss+xml',
      atom: 'application/atom+xml',
      css: 'text/css',
      csv: 'text/csv',
      all: '*/*',
      json: ['application/json', 'text/json'],
      form: 'application/x-www-form-urlencoded',
      multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
   development {
      grails.logging.jul.usebridge = true
   }
   production {
      grails.logging.jul.usebridge = false
      grails.serverURL = "http://pboksz.s156.eatj.com/mtg"
   }
}

/**
 * Directory configuration.
 * Pickup the Tomcat/Catalina directory else use the target or current dir.
 */
def fs = File.separator // Local variable.
globalDirs.targetDir = new File("target${fs}").isDirectory() ? "target${fs}" : ''
globalDirs.catalinaBase = System.properties.getProperty('catalina.base')
globalDirs.logDirectory = globalDirs.catalinaBase ? "${globalDirs.catalinaBase}${fs}logs${fs}" : globalDirs.targetDir
globalDirs.workDirectory = globalDirs.catalinaBase ? "${globalDirs.catalinaBase}${fs}work${fs}" : globalDirs.targetDir
globalDirs.searchableIndexDirectory = "${globalDirs.workDirectory}SearchableIndex${fs}${appName}${fs}"

/**
 * Log4j configuration.
 * Causing this file to reload (e.g. edit+save) may break the appLog destination
 * and further logs will be written to files or directories like "[:]".
 * For more info see http://logging.apache.org/log4j/1.2/manual.html
 * For log levels see http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/Level.html
 * Basic log levels are ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
 */
log4j = {
   appenders {
      // Use if we want to prevent creation of a stacktrace.log file.
      'null' name: 'stacktrace'
   }

   // This is for the built-in stuff and from the default Grails-1.2.1 config.
   error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
         'org.codehaus.groovy.grails.web.pages', //  GSP
         'org.codehaus.groovy.grails.web.sitemesh', //  layouts
         'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
         'org.codehaus.groovy.grails.web.mapping', // URL mapping
         'org.codehaus.groovy.grails.commons', // core / classloading
         'org.codehaus.groovy.grails.plugins', // plugins
         'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
         'org.springframework',
         'org.hibernate',
         'net.sf.ehcache.hibernate'

   error 'grails.app' // Set the default log level for our app code.
   error 'grails.app.service.AuthService'
   error 'grails.app.service.NavigationService'
   error 'grails.app.service.com.zeddware.grails.plugins.filterpane.FilterService'
   error 'grails.app.task' // Quartz jobs.

   info 'org.codehaus.groovy.grails.plugins.searchable'
   info 'grails.app.bootstrap' // Set the log level per type and per type.class
   info 'grails.app.task.InventoryIndexJob'

   warn 'grails.app.service'
   warn 'grails.app.controller'

   debug 'grails.app.service.AssetCsvService'
   debug 'grails.app.service.PersonCsvService'
   debug 'grails.app.service.InventoryCsvService'
   debug 'grails.app.service.AssetTreeService'
}
