document.addEventListener('DOMContentLoaded', () => {
    console.log("hello");
    const viewContactModal = document.getElementById('view_contact_modal');
    const options = {
        placement: 'bottom-right',
        backdrop: 'dynamic',
        backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
        closable: true,
        onHide: () => {
            console.log('modal is hidden');
        },
        onShow: () => {
            console.log('modal is shown');
        },
        onToggle: () => {
            console.log('modal has been toggled');
        },
    };

    // Instance options object
    const instanceOptions = {
        id: 'view_contact_modal',
        override: true
    };

    // Ensure `Modal` class is loaded correctly
    if (typeof Modal === 'undefined') {
        console.error("Modal class is not defined. Make sure it's imported.");
        return;
    }

    // Initialize the modal
    const contactModal = new Modal(viewContactModal, options, instanceOptions);

    // Attach functions to the window object for external access
    window.openContactModal = function() {
        if (contactModal) {
            contactModal.show();
        } else {
            console.error("contactModal is not defined");
        }
    };

    window.closeContactModal = function() {
        if (contactModal) {
            contactModal.hide();
        } else {
            console.error("contactModal is not defined");
        }
    };

    // Define loadContactdata function correctly
    window.loadContactdata = function(id) {
        if (contactModal) {
            console.log("Loading contact data for ID:", id);
            fetch(`http://localhost:8081/api/contact/${id}`)
                .then(response => response.json())
                .then(data => {
                    console.log("Fetched data:", data);
                    document.querySelector("#contact_name").innerHTML=data.name;
                    document.querySelector("#contact_email").innerHTML=data.email;
                    document.querySelector("#contact_image").src=data.picture;
                    document.querySelector("#contact_address").innerHTML=data.address;
                    document.querySelector("#contact_about").innerHTML=data.description;
                    document.querySelector("#contact_phone").innerHTML=data.phoneNumber;
                    document.querySelector("#contact_favorite").innerHTML=data.favorite;
                    // document.querySelector("#contact_linkedIn").innerHTML=data.LinkedIn;
                    // document.querySelector("#contact_website").innerHTML=data.Website;
                    document.querySelector("#contact_website").href = data.websiteLink;
                    document.querySelector("#contact_website").innerHTML = data.websiteLink;
                    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
                    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;



                    openContactModal();
                    // console.log(data.name);
                    // Add additional data handling here if needed
                })

                .catch(error => {
                    console.error("Error fetching data:", error);
                });
        } else {
            console.log("contactModal is not defined");
        }
    };
});

async function deletecontact(id) {

    // Swal.fire({
    //     title: "Do you want to delete the contact?",
        
    //     showCancelButton: true,
    //     confirmButtonText: "Delete",
    //     denyButtonText: `Don't save`
    //   }).then((result) => {
    //     /* Read more about isConfirmed, isDenied below */
    //     if (result.isConfirmed) {
    //       Swal.fire("Saved!", "", "success");
    //     } else if (result.isDenied) {
    //       Swal.fire("Changes are not saved", "", "info");
    //     }
    //   });
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {
            const url="http://localhost:8081/user/contact/delete/"+id;
            window.location.replace(url)
        //   Swal.fire({
        //     title: "Deleted!",
        //     text: "Your contact has been deleted.",
        //     icon: "success"
        //   });
        }
      });
    
}