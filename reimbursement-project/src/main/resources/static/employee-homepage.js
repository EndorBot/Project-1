// Initial page load
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'Finance Manager') {
            window.location.href = 'fm-homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }
    
    populateTableWithReimbursements();
});

// Logout btn
let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include'
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
        td2.innerHTML = reimbursement.reimbursementAmount

        let td3 = document.createElement("td"); // date submitted
        td3.innerHTML = reimbursement.reimbursementSubmitted;

        let td4 = document.createElement("td"); // date resolved
        td4.innerHTML = reimbursement.reimbursementResolved;

        let td5 = document.createElement("td"); // status
        //td5.innerHTML = reimbursement.status; ; done in the if else
        
        let td6 = document.createElement("td"); // type
        td6.innerHTML = reimbursement.type;

        let td7 = document.createElement('td'); // description
        td7.innerHTML = reimbursement.reimbursementDesc;

        let td8 = document.createElement('td'); // image becomes td12, used to be td8
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Receipt Image';
        td8.appendChild(viewImageButton);

        let td9 = document.createElement('td'); // author id
        td9.innerHTML = reimbursement.authorId;

        let td10 = document.createElement('td'); // FM id or finance manager id
        // td10.innerHTML = reimbursement.financeManagerId; done in the if else

        if (reimbursement.financeManagerId != 0) {
            td5.innerHTML = reimbursement.status;
            td10.innerHTML = reimbursement.financeManagerId;
        } else {
            td5.innerHTML = 'PENDING';
            td10.innerHTML = '-';
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
        });

        tr.appendChild(td1); // id
        tr.appendChild(td2); // Reimbursement Amount
        tr.appendChild(td3); // Date Submitted
        tr.appendChild(td4); // Date Resolved
        tr.appendChild(td5); // Status
        tr.appendChild(td6); // Type
        tr.appendChild(td7); // Reimbursement Description, old td2
        tr.appendChild(td8); // receipt image
        tr.appendChild(td9); // author id
        tr.appendChild(td10); // FM id

        tbodyElement.appendChild(tr);
    }
}

// Submitting reimbursement
let reimbursementSubmitButton = document.querySelector('#submit-reimbursement-btn');

reimbursementSubmitButton.addEventListener('click', submitReimbursement);

async function submitReimbursement() {
    // Need to have these fields because these values cannot be null in the database
    let reimbursementAmount = document.querySelector('#reimbursement-amount');
    let reimbursementType = document.querySelector('#reimbursement-type');// change to drop down relation
    let reimbursementDescInput = document.querySelector('#reimbursement-description');
    let reimbursementImageInput = document.querySelector('#reimbursement-file');

    const file = reimbursementImageInput.files[0];

    let formData = new FormData();
    formData.append('reimb_amount', reimbursementAmount.value);
    formData.append('reimb_type', reimbursementType.value);
    formData.append('reimb_decription', reimbursementDescInput.value); // have to match database name
    formData.append('reimb_recipt', file);

    let res = await fetch('http://localhost:8080/reimbursements', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) { // If we successfully added an reimbursement
        populateTableWithReimbursements(); // Refresh the table of reimbursements
    } else if (res.status === 400) { // error message
        let reimbursementForm = document.querySelector('#reimbursement-submit-form');

        let data = await res.json();

        let pTag = document.createElement('p');
        pTag.innerHTML = data.message;
        pTag.style.color = 'red';

        reimbursementForm.appendChild(pTag);
    }
}