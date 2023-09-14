document.addEventListener("DOMContentLoaded", function () {
    const roomForm = document.getElementById("roomForm");
    const saveButton = document.getElementById("saveButton");
    const cancelButton = document.getElementById("cancelButton");
    const confirmMessage = document.getElementById("confirm");
    const membersInput = document.getElementById("members");
    const membersList = document.getElementById("membersList");

    const selectedMembers = []; // Array to store selected members

    // Event listener for typing in the "Members" input field
    membersInput.addEventListener("input", function () {
        const searchText = membersInput.value.trim().toLowerCase();

        // Clear the previous members list
        membersList.innerHTML = "";

        // Filter members based on the search text
        const filteredMembers = dummyMembers.filter(function (member) {
            return member.toLowerCase().includes(searchText);
        });

        // Add filtered members to the list
        filteredMembers.forEach(function (member) {
            const listItem = document.createElement("li");
            listItem.textContent = member;

            // Event listener to add a member when clicked
            listItem.addEventListener("click", function () {
                if (!selectedMembers.includes(member)) {
                    selectedMembers.push(member);
                    updateSelectedMembers();
                }
            });

            membersList.appendChild(listItem);
        });
    });

    // Function to update the list of selected members
    function updateSelectedMembers() {
        membersList.innerHTML = "";
        selectedMembers.forEach(function (member) {
            const listItem = document.createElement("li");
            listItem.textContent = member;
            membersList.appendChild(listItem);
        });
    }

    // Event listener for the "Save" button
    saveButton.addEventListener("click", function () {
        if (validateForm()) {
            // Form is valid, you can save the data or display a confirmation message
            confirmMessage.innerHTML = "Meeting details saved successfully.";
            // You can add code to save the data here, including selectedMembers array
        }
    });

    // Event handler for Cancel button
    cancelButton.addEventListener("click", function () {
        // Reset the form and clear any confirmation messages
        roomForm.reset();
        confirmMessage.innerHTML = "";
        selectedMembers.length = 0; // Clear selected members array
        updateSelectedMembers();
    });

    // Form validation function
    function validateForm() {
        // Add your form validation logic here
        // Ensure all required fields are filled, e.g., roomName, meetingDate, etc.
        return true; // Form is valid
    }

    // Dummy data for members (replace with your actual data)
    const dummyMembers = [
        "John Doe",
        "Jane Smith",
        "Alice Johnson",
        "Bob Brown",
        "Carol Wilson",
        "David Lee",
        "Eva Garcia",
        "Frank Davis",
        "Grace Martinez",
        "Henry Taylor",
    ];
});
