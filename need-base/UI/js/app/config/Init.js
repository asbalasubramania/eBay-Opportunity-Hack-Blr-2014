require.config({
    baseUrl:"./js/app",
    // 3rd party script alias names (Easier to type "jquery" than "libs/jquery, etc")
    // probably a good idea to keep version numbers in the file names for updates checking
    paths:{
        // Core Libraries
        "jquery":"../libs/jquery",
        "underscore":"../libs/lodash",
        "backbone":"../libs/backbone",
        "marionette":"../libs/backbone.marionette",
        "handlebars":"../libs/handlebars",

        
        "text":"../libs/text"
    },
    // Sets the configuration for your third party scripts that are not AMD compatible
    shim:{
        "backbone":{
            "deps":["underscore"],
            // Exports the global window.Backbone object
            "exports":"Backbone"
        },
        "marionette":{
            "deps":["underscore", "backbone", "jquery"],
            // Exports the global window.Marionette object
            "exports":"Marionette"
        },
        "handlebars":{
            "exports":"Handlebars"
        }
    }
});

// Includes Desktop Specific JavaScript files here (or inside of your Desktop router)
require(["App", "routers/AppRouter", "controllers/Controller",  "jquery"],
    function (App, AppRouter, Controller) {
        App.appRouter = new AppRouter({
            controller:new Controller()
        });
        App.start();
    });