<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>

<head>
<title>Apptodate</title>
<link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
<link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.2.0/css/bootstrap.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/dashboard.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/app.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/angular-ngtable/ng-table.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/font-awesome-4.1.0/css/font-awesome.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/angular/toaster.css' />" />
<script src="<@spring.url '/lib/jquery/jquery.min.js' />"></script>
<style>
    .navbar { border-radius:0; }
</style>
<script data-main="<@spring.url '/tenant/main.js' />" src="<@spring.url '/lib/require/require.js' />"></script>
</head>

<body>
  <!-- NAVIGATION -->
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="<@spring.url '/#/' />">Apptodate</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right" ng-controller="AuthController">
        <li class="dropdown ng-hide" ng-show="principal.principal">
          <a href="javascript:;" class="dropdown-toggle">
            {{principal.principal.username}}<span class="caret" style="margin-left: 5px;"></span>
          </a>
          <ul class="dropdown-menu" role="menu">
            <li ng-if="!hasRole('ROLE_ADMIN')"><a href="<@spring.url '/#/' />">Home</a></li>
            <li ng-if="hasRole('ROLE_ADMIN')"><a href="<@spring.url '/#/admin' />">Home</a></li>
            <li ng-if="hasRole('ROLE_ADMIN')"><a ui-sref="admin_txn">Transaction & Reports</a></li>
            <li><a href="<@spring.url '/logout' />">Logout</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-right" style="width: 300px;">
        <input type="text" class="form-control" placeholder="Search Customer records..." style="width: 100%;">
      </form>
      </div>
    </div>
  </nav>
  <!-- 
  <nav class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
      <a class="navbar-brand" href="<@spring.url '/#/' />">App to date
         <small style="font-size: 0.5em;">Powered by Chikka</small>
      </a>
    </div>
    <ul class="nav navbar-nav" ng-controller="AuthController">
      <li class="dropdown ng-hide" ng-show="principal.principal">
        <a href="javascript:;" class="dropdown-toggle">
          {{principal.principal.username}}<span class="caret" style="margin-left: 5px;"></span>
        </a>
        <ul class="dropdown-menu" role="menu">
          <li ng-if="!hasRole('ROLE_ADMIN')"><a href="<@spring.url '/#/' />">Home</a></li>
          <li ng-if="hasRole('ROLE_ADMIN')"><a href="<@spring.url '/#/admin' />">Home</a></li>
          <li ng-if="hasRole('ROLE_ADMIN')"><a ui-sref="admin_txn">Transaction & Reports</a></li>
          <li><a href="<@spring.url '/help' />">Help</a></li>
          <li><a href="<@spring.url '/info' />">Info / Test</a></li>
          <li><a href="<@spring.url '/logout' />">Logout</a></li>
        </ul>
      </li>
    </ul>
  </nav>
  -->
  <!-- Main Content -->
  <div class="container-fluid">
    <toaster-container toaster-options="{'time-out': 4000, 'limit' : 3}"></toaster-container>
    <div class="row">
      <div ui-view="sidebar"></div>
      <div class="col-sm-10 main">
        <div ui-view="main">Loading resources...</div>
      </div>
    </div>
  </div>

  <div style="margin-bottom: 30px;"></div>
</body>

</html>
