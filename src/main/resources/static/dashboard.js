// Example of how you could refresh data periodically
window.onload = function() {
    setInterval(refreshStockData, 60000);
}

function refreshStockData() {
    location.reload();
}
