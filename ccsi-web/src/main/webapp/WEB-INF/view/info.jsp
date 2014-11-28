<#import "/spring.ftl" as spring />

<html>
<head>
  <title>Info</title>
  <link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.0.3/css/bootstrap.min.css' />">
  <link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
  <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</head>

<body>
  <div class="container">
    <div class="col-sm-12">
      <h1><a href="<@spring.url '/' />">Apptodate</a> > Info</h1>

      <div class="panel panel-info">
        <div class="panel-heading">Check status</div>
        <div class="panel-body">
          <div class="col-sm-12" id="test-response" style="font-size: 1.1em;">
            <strong>Send a test message to receive a response.</strong>
          </div>
          <div class="col-sm-12" style="margin-top: 20px;"></div>
          <form id="form-test">
            <div class="form-group">
              <div class="col-sm-4">
                <input type="text" id="test-input" class="form-control" />
              </div>
              <input type="submit" class="btn btn-success" value="Send Test Message" />
            </div>
          </form>
        </div>
      </div>

      <p><a href="<@spring.url '/' />">Back to Apptodate</a>
    </div>
  </div>

  <div class="mt10">&nbsp;</div>
</body>
</html>

<script type="text/javascript">
$(function() {
  var $ipt = $('#test-input'), $resp = $('#test-response');
  $('#form-test').submit(function() {
    var msg = $ipt.val();
    if (!msg.trim()) {
      return false;
    }
    $.get('../testmsg', {msg: msg}, function (resp) {
      $resp.find('strong').text('292902274: ' + resp);
    });

    return false;
  });
});
</script>