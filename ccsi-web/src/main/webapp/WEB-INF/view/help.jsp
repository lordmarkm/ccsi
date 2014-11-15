<#import "/spring.ftl" as spring />

<html>
<head>
  <title>Help</title>
  <link rel="stylesheet" href="<@spring.url '/lib/bootstrap-3.0.3/css/bootstrap.min.css' />">
  <link rel="icon" type="image/x-icon" href="<@spring.url '/images/1412091097_48493.ico' />" />
</head>

<body>
  <div class="container">
    <div class="col-sm-12">
      <h1><a href="<@spring.url '/' />">Apptodate</a> > Help</h1>
      <p>For a short summary of Apptodate, please watch our demo video:
      <div class="text-center">
        <iframe width="560" height="315" src="//www.youtube.com/embed/Hm2Z2wOpZW0" frameborder="0" allowfullscreen></iframe>
      </div>
      
      <h2>Businesses</h2>
      <p>A business is a single business, organization, person, or any other entity using App-to-date to interact with their customers
         or employees.
      <ol>
        <li><strong>Business Name</strong> - The name you want your business to be known by
        <li><strong>Keyword</strong> - This is the keyword that your customers will use to indicate that they want to interact with your business.
          Every message meant for your business will begin with this keyword. <strong>Keywords must be alphanumberic and be a single
          word. Capitalization is ignored.</strong> Leading and trailing whitespaces will be removed.
      </ol>
      
      <h2>Keyword templates</h2>
      <p>Keyword templates are triggered by a predefined keyword. When a customer sends the keyword, the system will automatically reply
         with the keyword template's reply. <strong>Keyword templates do not use replaceable strings.</strong>
      <ol>
        <li>Keyword - The keyword used to trigger the template.
        <li>Reply - The reply that will be sent out.
      </ol>
      
      <h2>Templates</h2>
      <ol>
        <li>Status - This is the status of a customer record. Common examples are 'Ready', 'Not ready', 'Paid', 'Processed', etc.
        <li>Template String - this is what the application will send back to the customer as an SMS if that customer's record's status matches the  template's status.
      </ol>
      <h2>Universal replaceable strings</h2>
      <p>Given the following sample customer record
      <div class="alert alert-info">
        <dl class="dl-horizontal">
          <dt>Tracking No.
          <dd>ABC12
          <dt>Customer name
          <dd>Mark Martinez
          <dt>Transaction type
          <dd>laundry
        </dl>
      </div>
  
      <p>You can use these strings to make personalized SMS replies:
  
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Replaceable string
            <th>Description
            <th>Example Template String
            <th>Message after replacement
          </th>
        </thead>
        <tbody>
          <tr>
            <td>&lt;trackingNo&gt;
            <td class="small">The automatically generated tracking number
            <td>Your order with tracking no. &lt;trackingNo&gt; is ready for pickup!
            <td>Your order with tracking no. ABC12 is ready for pickup!
          </tr>
          <tr>
            <td>&lt;customerName&gt;
            <td class="small">The value set in Customer Name
            <td>Hello Mr./Ms. &lt;customerName&gt;, how are you today?
            <td>Hello Mr./Ms. Mark Martinez, how are you today?
          </tr>
          <tr>
            <td>&lt;transactionType&gt;
            <td class="small">The value set in Transaction type
            <td>Hello Mr./Ms. &lt;customerName&gt;, your &lt;transactionType&gt; is ready for pickup!
            <td>Hello Mr./Ms. Mark Martinez, how are you today?
          </tr>
        </tbody>
      </table>

      <h2>Custom Variables</h2>
      <p>Custom variables are similar to the &lt;customerName&gt; and &lt;transactionType&gt; variables in that they are replaced in replies
         by values that you define. 
      <p>When you create a custom variable for your business, you are required to enter a default value. This value
         is used when a customer record has no defined value for this variable. However, by going to the customer record's preview page
         and viewing advanced options, you may enter a custom value. This will override the default.

      <p><a href="<@spring.url '/' />">Back to Apptodate</a>
    </div>
  </div>

  <div class="mt10">&nbsp;</div>
</body>
</html>
