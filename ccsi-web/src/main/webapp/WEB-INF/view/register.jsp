<#import "/spring.ftl" as spring />

<html>
<head>
  <link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.0.3/css/bootstrap.min.css' />">
  <link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
  <title>Register</title>
</head>

<body>
  <div class="container">
    <div class="col-sm-12">
      <div class="col-sm-6 col-md-offset-3" style="margin-top: 100px;">
        <div class="panel panel-success">
          <div class="text-center panel-heading">
            <div class="panel-title"><strong>App to date</strong> <br/><small>Powered by Chikka</small></div>
          </div>
          <div class="panel-body">
            <#if msg??><div class="alert alert-danger text-center">${msg }</div></#if>
            <form action="<@spring.url '/register' />" method="post" class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-4 control-label">Username</label>
                <div class="col-sm-8">
                  <input type="text" name="username" class="form-control" <#if form??>value="${form.username }"</#if> />
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-4 control-label">Password</label>
                <div class="col-sm-8">
                  <input type="password" name="password" class="form-control" <#if form??>value="${form.password }"</#if> />
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-4 control-label">Confirm password</label>
                <div class="col-sm-8">
                  <input type="password" name="confirmPassword" class="form-control" <#if form??>value="${form.confirmPassword }"</#if> />
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                  <input type="submit" value="Register" class="btn btn-primary form-control"/>
                </div>
                <div class="col-sm-4">
                  <a href="<@spring.url '/auth/login' />" class="btn btn-default form-control">Cancel</a>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>