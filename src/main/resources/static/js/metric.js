$(document).ready(function () {
    function getSystemHealthStatus() {
        fetch('http://localhost:8080/actuator/health')
            .then(response => response.json())
            .then(data => {
                const offLight = document.getElementById('offLight');
                const onLight = document.getElementById('onLight');

                if (data.status === 'UP') {
                    offLight.style.opacity = '0.1';
                    onLight.style.opacity = '1';
                } else {
                    offLight.style.opacity = '1';
                    onLight.style.opacity = '0.1';
                }
            });
    }

    // check it every 5 minutes
    setInterval(getSystemHealthStatus, 300000);

    getSystemHealthStatus();
});