<%@ page import="db.LiftRide" %>
<html>
<body>
    <h2>Hello World!</h2>
    <div id="liftRidesContainer">
        <%= LiftRide.getLiftRides() %>
    </div>
</body>
</html>
