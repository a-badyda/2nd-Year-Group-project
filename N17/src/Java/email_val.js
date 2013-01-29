<script type="text/javascript">
//<![CDATA[
        /**
        The validate function checks that a correct email and 16 digit bank card number has been entered
        */
        function validate()
        {
        
                var email = document.getElementById('email');
                var validate = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                                       
                if(!validate.test(email.value))
                {
                        alert('Please enter a valid email address');
                        email.focus;
                        var emailchk = 0;
                }else{
                        var emailchk = 1;
                }

                if(emailchk == 1 &&  bankchk == 1)
                {
                        alert('Payment Complete');
                }
        }
//]]>
</script>

