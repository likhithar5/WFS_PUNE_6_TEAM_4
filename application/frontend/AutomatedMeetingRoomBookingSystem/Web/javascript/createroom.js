document.addEventListener("DOMContentLoaded", function () {
    const roomForm = document.getElementById("roomForm");
    const saveButton = document.getElementById("saveButton");
    const cancelButton = document.getElementById("cancelButton");
    const confirmMessage = document.getElementById("confirm");

    saveButton.addEventListener("click", function () {
        if (validateForm()) {
            // Form is valid, you can save the data or display a confirmation message
            confirmMessage.innerHTML = "Meeting room details saved successfully.";
            // You can add code to save the data here
        }
    });

    // Event handler for Cancel button
    cancelButton.addEventListener("click", function () {
        // Reset the form and clear any confirmation messages
        roomForm.reset();
        confirmMessage.innerHTML = "";
    });

    // Form validation function
    function validateForm() {
        const roomName = document.getElementById("roomName").value;
        const seatingCapacity = document.getElementById("seatingCapacity").value;
        const amenitiesCheckboxes = document.querySelectorAll('input[name="amenities"]:checked');

        // Add your validation logic here
        if (roomName.trim() === "") {
            alert("Room Name is required.");
            return false;
        }

        if (seatingCapacity <= 0) {
            alert("Seating Capacity must be greater than 0.");
            return false;
        }

        if (amenitiesCheckboxes.length < 2) {
            alert("Select at least 2 Amenities.");
            return false;
        }

        return true; // Form is valid
    }
});