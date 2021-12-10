// Check to see if the user is logged in or not, and if not, relocate them back to
// login screen
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Employee') {
            window.location.href = 'employee-homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    // If we make it past the authorization checks, call another function that will 
    // retrieve all reimbursements
    populateTableWithReimbursements();

});

// Logout btn
let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        method: 'POST',
        credential: 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }

})


async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector("#reimbursements-table tbody");
    tbodyElement.innerHTML = '';
    let reimbursementArray =  await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let td1 = document.createElement('td'); // id
        td1.innerHTML = reimbursement.id;

        let td2 = document.createElement("td"); // amount
        td2.innerHTML = reimbursement.reimbursementAmount;

        let td3 = document.createElement("td"); // date submitted, might be handled differently when timestamp is implemented
        td3.innerHTML = reimbursement.reimbursementSubmitted;

        let td4 = document.createElement("td"); // date resolved, might be handled differently when timestamp is implemented
        td4.innerHTML = reimbursement.reimbursementResolved;

        let td5 = document.createElement("td"); // status
        // td5.innerHTML = reimbursement.status;  done in the if else
        
        let td6 = document.createElement("td"); // type
        td6.innerHTML = reimbursement.type;

        let td7 = document.createElement('td'); // description
        td7.innerHTML = reimbursement.reimbursementDesc;

        let td8 = document.createElement('td'); // author id, used to be td9, now 8
        td8.innerHTML = reimbursement.authorId;

        let td9 = document.createElement('td'); // FM id or finance manager id, used to be td10, now 9
       // td9.innerHTML = reimbursement.financeManagerId; done in the if else

        let td10 = document.createElement('td'); // update status button
        let td11 = document.createElement('td'); // status input

        let td12 = document.createElement('td'); // image becomes td12, used to be td8
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Receipt Image';
        td12.appendChild(viewImageButton);


        if (reimbursement.financeManagerId != 0) {
            td5.innerHTML = reimbursement.status;
            td9.innerHTML = reimbursement.financeManagerId;
        } else {
            td5.innerHTML = 'PENDING';
            td9.innerHTML = '-';
            /*
            function statusInput(){
                document.getElementById("statusDropdown").classList.toggle("show");
            }

            window.onclick = function(event) {
                if (!event.target.matches('.statusbtn')) {
                    let dropdowns = document.getElementsByClassName("dropdown-content");
                    let i;
                    for (i = 0; i < dropdowns.length; i++){
                        let openDropdown = dropdowns[i];
                        if (openDropdown.classList.contains('show')){
                            openDropdown.classList.remove('show');
                        }
                    }
                }
            }
			*/
            // update status button and input
            let statusInput = document.createElement('input');
            statusInput.setAttribute('type',String);

            let statusButton = document.createElement('button');
            statusButton.innerText = 'Add Reimbursement Status';
            statusButton.addEventListener('click', async () => {
                let res = await fetch(`http://localhost:8080/reimbursements/${reimbursement.id}/status`, 
                    {
                        credentials: 'include',
                        method: 'PATCH',
                        body: JSON.stringify({
                            status: statusInput.value
                        })
                    });

                if (res.status === 200) {
                    populateTableWithReimbursements(); // Calling the function to repopulate the entire
                    // table again
                }
            });

            td10.appendChild(statusButton);
            td11.appendChild(statusInput);
        }

        viewImageButton.addEventListener('click', () => {
            // Add the is-active class to the modal (so that the modal appears)
            // inside of the modal on div.modal-content (div w/ class modal-content)
            //  -> img tag with src="http://localhost:8080/reimbursement/{reimb_id}/image"
            let reimbursementImageModal = document.querySelector('#reimbursement-image-modal');

            // Close button
            let modalCloseElement = reimbursementImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimbursementImageModal.classList.remove('is-active');
            });

            // you can take an element and use querySelector on it to find the child elements
            // that are nested within it
            let modalContentElement = reimbursementImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimbursement/${reimbursement.id}/image`);
            modalContentElement.appendChild(imageElement);

            reimbursementImageModal.classList.add('is-active'); // add a class to the modal element to have it display
        })

        tr.appendChild(td1); // id
        tr.appendChild(td2); // Reimbursement Amount
        tr.appendChild(td3); // Date Submitted
        tr.appendChild(td4); // Date Resolved
        tr.appendChild(td5); // Status
        tr.appendChild(td6); // Type
        tr.appendChild(td7); // Reimbursement Description, old td2       
        tr.appendChild(td8); // author id
        tr.appendChild(td9); // FM id
        tr.appendChild(td10); // update status button
        tr.appendChild(td11); // status input
        tr.appendChild(td12); // receipt image
        tbodyElement.appendChild(tr);
    }
}