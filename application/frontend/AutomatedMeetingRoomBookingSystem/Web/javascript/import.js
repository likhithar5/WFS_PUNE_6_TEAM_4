const xmlFileInput = document.getElementById('xmlFileInput');
const importButton = document.getElementById('importButton');
const selectedFileName = document.getElementById('selectedFileName');

importButton.addEventListener('click', function () {
    xmlFileInput.click(); // Trigger the file input click event
});

xmlFileInput.addEventListener('change', function () {
    if (xmlFileInput.files.length > 0) {
        const fileName = xmlFileInput.files[0].name;
        selectedFileName.textContent = `Selected file: ${fileName}`;
    } else {
        selectedFileName.textContent = '';
    }
});