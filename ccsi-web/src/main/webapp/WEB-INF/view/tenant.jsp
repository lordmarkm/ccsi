<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>

<head>
<title>CCSI</title>
<link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
<link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.2.0/css/bootstrap.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/angular-ngtable/ng-table.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/font-awesome-4.1.0/css/font-awesome.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/angular/toaster.css' />" />
<link rel="stylesheet" href="<@spring.url '/tenant/app.css' />" />
<script src="<@spring.url '/lib/jquery/jquery.min.js' />"></script>
<style>
    .navbar { border-radius:0; }
</style>
<script data-main="<@spring.url '/tenant/main.js' />" src="<@spring.url '/lib/require/require.js' />"></script>
</head>

<body>
  <!-- NAVIGATION -->
  <nav class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
      <a class="navbar-brand" href="<@spring.url '/#/' />">Chikka Service Interface</a>
    </div>
    <ul class="nav navbar-nav">
      <!-- 
      <li><a href="javascript:;">Hello</a></li>
      -->
    </ul>

    <ul class="nav navbar-nav" ng-controller="AuthController">
      <li class="dropdown ng-hide" ng-show="principal.principal">
        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
          {{principal.principal.username}}<span class="caret" style="margin-left: 5px;"></span>
        </a>
        <ul class="dropdown-menu" role="menu">
          <!--
          <li><a href="#/profile">Profile</a></li>
          -->
          <li><a href="<@spring.url '/#/' />">Home</a></li>
          <li><a href="<@spring.url '/logout' />">Logout</a></li>
        </ul>
      </li>
    </ul>
  </nav>

  <!-- Main Content -->
  <div class="container">
    <toaster-container toaster-options="{'time-out': 4000, 'limit' : 3}"></toaster-container>
    <div ui-view>Loading resources...</div>
  </div>

  <div style="margin-bottom: 30px;"></div>
</body>

</html>