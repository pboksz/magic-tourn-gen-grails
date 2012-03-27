<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<%@ page contentType="text/html;charset=UTF-8" %>
<head>

   <script type="text/javascript">

      function dropCheck(who)
      {
         return confirm("Do you really want to drop " + who + " from the tournament?");
      }

      function addRounds(numPlayers)
      {
         if((numPlayers >= 4) && numPlayers <= 8){
            clearRounds()
         }
         if((numPlayers > 8) && (numPlayers <= 16)) {
            clearRounds();
            addOptions(1);
         }
         if((numPlayers > 16) && (numPlayers <= 32)){
            clearRounds();
            addOptions(2);
         }
      }

      function clearRounds() {
         document.settings.howManyRounds.options.length = 1;
      }

      function addOptions(num) {
         for(var i=1; i<(num+1); i++){
            document.settings.howManyRounds.options[i] = new Option(i+3);
         }
      }

      //TODO still testing this, but coloring works individually
      function verifyValue(field, maxNum) {
         var hasError = true;
         var num = field.value;

         var opponent;
         if(this == "wins") {
            opponent = field.parent.parent.cells[4];
         }
         if(this == "losses") {
            opponent = field.parent.parent.cells[1];
         }
         if(opponent == "undefined"){
            opponent = 0;
         }

         //these are the only times it has no error
         if((num == maxNum) && (opponent < maxNum) && (opponent >= 0)) {
            hasError = false;
         }
         if((opponent == maxNum) && (num < maxNum) && (num >= 0)) {
            hasError = false;
         }

//         if((num > maxNum) || (num < 0)) {
//            hasError = true;
//         }
//         else {
//            hasError = false;
//         }

         //if there is an error do a red outline
         if(hasError){
            field.style.borderColor = "#ff8888";
            field.style.mozBoxShadow = "0 0 0.25em #ff8888";
            field.style.boxShadow = "0 0 0.25em #ff8888";
         }
         //else do a blue one
         else {
            field.style.borderColor = "#b2d1ff";
            field.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
            field.style.boxShadow = "0 0 0.25em #b2d1ff";
         }
      }
   </script>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   <title>Magic Tournament Generator | ${flash.title}</title>
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
   <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
   <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
   <g:layoutHead/>
   <r:layoutResources/>
</head>

<body>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="grailsLogo" role="banner">
   <g:link action="newtournament" style="margin: 0">
      <img src="${resource(dir: 'images', file: 'mtg-header.png')}" alt="Magic Tournament Generator"/>
   </g:link>
</div>

<div class="header">
   ${flash.title}
</div>

<g:if test="${flash.message}">
   <div class="message">
      ${flash.message}
   </div>
</g:if>

<g:if test="${flash.error}">
   <div class="errors">
      ${flash.error}
   </div>
</g:if>
<g:layoutBody/>
<div class="footer" role="contentinfo">Copyright © 2012 Phillip Boksz</div>
<g:javascript library="application"/>
<r:layoutResources/>
</body>
</html>