document.addEventListener('DOMContentLoaded', function () {
    const imageInput = document.getElementById('item-images');
    const previewContainer = document.getElementById('image-preview-container');
    const dt = new DataTransfer(); // DataTransfer object to modify the FileList
    const maxFiles = 5; // Maximum number of files allowed

    imageInput.addEventListener('change', function (event) {
        Array.from(event.target.files).forEach(file => {
            // Check if the file already exists in DataTransfer
            let isDuplicate = false;
            for (let i = 0; i < dt.items.length; i++) {
                if (dt.items[i].getAsFile().name === file.name && dt.items[i].getAsFile().size === file.size) {
                    isDuplicate = true;
                    break;
                }
            }

            // Prevent adding more than maxFiles
            if (dt.items.length >= maxFiles) {
                alert(`You can only upload a maximum of ${maxFiles} files.`);
                return;
            }

            if (!isDuplicate) {
                // Add file to the DataTransfer object
                dt.items.add(file);

                // Create preview container
                const previewDiv = document.createElement('div');
                previewDiv.style.display = 'inline-block';
                previewDiv.style.margin = '10px';
                previewDiv.style.position = 'relative';

                // Create image preview
                const img = document.createElement('img');
                img.src = URL.createObjectURL(file);
                img.style.maxWidth = '150px';
                img.style.border = '1px solid #ccc';
                img.style.borderRadius = '5px';

                // Create remove button
                const removeBtn = document.createElement('button');
                removeBtn.textContent = 'X';
                removeBtn.style.position = 'absolute';
                removeBtn.style.top = '5px';
                removeBtn.style.right = '5px';
                removeBtn.style.backgroundColor = '#ff4d4d';
                removeBtn.style.color = '#fff';
                removeBtn.style.border = 'none';
                removeBtn.style.borderRadius = '50%';
                removeBtn.style.width = '24px';
                removeBtn.style.height = '24px';
                removeBtn.style.cursor = 'pointer';

                removeBtn.addEventListener('click', function (e) {
                    e.preventDefault();

                    // Find and remove the file from the DataTransfer object
                    for (let i = 0; i < dt.items.length; i++) {
                        if (dt.items[i].getAsFile() === file) {
                            dt.items.remove(i);
                            break;
                        }
                    }

                    // Update the file input's FileList
                    imageInput.files = dt.files;

                    // Remove the preview
                    previewDiv.remove();
                });

                // Append image and remove button to the preview div
                previewDiv.appendChild(img);
                previewDiv.appendChild(removeBtn);
                previewContainer.appendChild(previewDiv);
            }
        });

        // Update the file input's FileList with the modified DataTransfer object
        imageInput.files = dt.files;
    });
});
