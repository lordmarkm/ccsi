<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>

<head>
<title>GEMS</title>
<link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
<link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.2.0/css/bootstrap.min.css' />" />
<link rel="stylesheet" href="<@spring.url '/lib/sidebar.css' />" />
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
        <a class="navbar-brand" href="<@spring.url '/#/' />">Geographical Effort Management System</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
      <form ng-controller="NavSearchController" class="navbar-form navbar-right form-inline" style="width: 300px;">
        <div class="col-sm-2">
        <button class="btn btn-large" title="Login"><i class="fa fa-user"></i></button>
        </div>
        <div class="col-sm-10">
        <input type="text" ng-model="typeahead"
         typeahead-watch-changes
         onclick="this.select();"
         typeahead="record as record.customerName for record in searchCustomers($viewValue)"
         typeahead-template-url="customTemplate.html"
         typeahead-loading="loadingLocations"
         typeahead-input-formatter="gotoPreview($model)"
         class="form-control" placeholder="Search Organizations..." style="width: 100%;">
         </div>
      </form>
      </div>
    </div>
  </nav>

  <!-- Main Content -->
  <div class="container-fluid">
    <toaster-container toaster-options="{'time-out': 4000, 'limit' : 3}"></toaster-container>
    <div class="row">
      <div ui-view="sidebar"></div>
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <div ui-view="main">Loading resources...</div>
      </div>
    </div>
  </div>

  <div style="margin-bottom: 30px;"></div>
</body>

</html>

<script type="text/ng-template" id="customTemplate.html">
  <a href="javascript:;">
      <strong>{{match.model.customerName}}</strong><br/>
      <small>{{match.model.trackingNo}} | {{match.model.transactionType}}</small>
  </a>
</script>