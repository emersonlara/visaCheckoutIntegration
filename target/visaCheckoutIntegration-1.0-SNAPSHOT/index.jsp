<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>        
        <title>
            My Checkout
        </title>        
        <script type="text/javascript">
            function onVisaCheckoutReady() {
                var currencyValue = document.getElementById('currency').value;
                V.init({
                    apikey: "7O07VN664O10JW6A9ESS113p8sf9JeGzr6_2haC9F9m_ANtLM",
                    paymentRequest: {
                        currencyFormat: "USD###,###,###."
                        /*total: "12.00",*/
                        /*description: "My Product",*/  
                        /*orderId: "102030"*/
                    }
                }
                );
                V.on("payment.success", function (payment)
                {
                    document.getElementById('payload').value = JSON.stringify(payment);             
                    alert(JSON.stringify(payment));
                });
                V.on("payment.cancel", function (payment)
                {
                    alert(JSON.stringify(payment));
                });
                V.on("payment.error", function (payment, error)
                {
                    alert(JSON.stringify(error));
                });

            }
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : evt.keyCode
                return !(charCode > 31 && (charCode < 48 || charCode > 57));
            }
        </script>

        <style type="text/css">
            body {
                background-color: #d3d3d3
            }
            body { font-family: Arial; font-size: 0.4cm }            
        </style>

    </head>
    <body>
        <div id="wrap" class="cf">
            <div id="head" class="cf">
            </div>     
            <div id="body" class="cf">
                <form action="/visaCheckoutIntegration/payment" id="FormCheckout" method="post">
                    <fieldset id="step-info" class="step-info">
                        <div id="connect-options-xquelo" class="infobox">
                            <div style="padding: 5px; float: left">
                                <p><h1>Select Visa Checkout Payment to Start</h1></p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="visacheckout" class="visacheckout">
                        <img
                            alt="Visa Checkout"
                            class="v-button"
                            onclick="alertTest()"
                            id="button"
                            role="button"
                            src="https://sandbox.secure.checkout.visa.com/wallet-services-web/xo/button.png?cardBrands=VISA,MASTERCARD,DISCOVER,AMEX"/>
                        <p id="payloadArea">
                            <label>Payload Encrypted</label><br />
                            <textarea cols=120 id="payload" rows="10" name="payload" maxlength="500" wrap="hard" readonly="true"></textarea>
                        </p>
                        <input type="hidden" id="originalPayload"/>

                    </fieldset>

                    <fieldset id="step-payment" class="step-payment">
                        <p id="product">
                            <label>Select the product</label><br />
                            <select id="product" name="product" class="select_product" >
                                <option value="Cellphone">Cellphone</option>
                                <option value="Cellphone">Tablet</option>
                                <option value="Computer">Computer</option>
                            </select>
                        </p>
                        <p id="currency">
                            <label>Select the currency and set the price</label><br />
                            <select id="currency" name="currency" class="select_currency">
                                <option value="USD">USD</option>
                                <option value="BRL">BRL(Brazilian Real)</option>
                            </select>
                            <input id="price" onkeypress="return isNumberKey(event);" name="price" type="number" min="0" max="1000000" data-number-to-fixed="2" data-number-stepfactor="100" class="currency" value="100"/><br>                            
                        </p>
                        <p class="submit">
                            <button type="submit" id="BtnPay" class="button">Confirm Your Payment</button><br/>
                        </p>                        
                    </fieldset>                   
                    <script type="text/javascript"
                            src="https://sandbox-assets.secure.checkout.visa.com/checkout-widget/resources/js/integration/v1/sdk.js">
                    </script>                        
                </form>
            </div>    
    </body>
</html>