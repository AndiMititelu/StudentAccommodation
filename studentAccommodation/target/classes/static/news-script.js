    const editButtons = document.querySelectorAll('.edit-btn');
    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            const newsPost = button.closest('.news-post');
            const title = newsPost.querySelector('.title');
            const content = newsPost.querySelector('.content');

            title.contentEditable = true;
            content.contentEditable = true;
            title.focus();
            button.textContent = 'Save';

            button.addEventListener('click', function() {
                // Perform AJAX request to update news post on the server
                // Use Fetch API or a library like jQuery.ajax

                // After successful update, update the UI
                title.contentEditable = false;
                content.contentEditable = false;
                button.textContent = 'Edit';
            });
        });
    });