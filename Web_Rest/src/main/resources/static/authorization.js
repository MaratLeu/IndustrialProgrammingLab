function applyInputEngine() {
    const engine = document.getElementById('engineInput').value;
    switch(engine) {
        case 'archive':
            archive();
            break;
        case 'encrypt':
            encrypt();
            break;
        case 'archive_then_encrypt':
            archiveThenEncrypt();
            break;
        case 'encrypt_then_archive':
            encryptThenArchive();
            break;
    }
}

function applyOutputEngine() {
    const engine = document.getElementById('engineOutput').value;
    switch(engine) {
        case 'archive':
            archive();
            break;
        case 'encrypt':
            encrypt();
            break;
        case 'archive_then_encrypt':
            archiveThenEncrypt();
            break;
        case 'encrypt_then_archive':
            encryptThenArchive();
            break;
    }
}

function archive() {
    alert('Archive operation selected');
}

function encrypt() {
    alert('Encrypt operation selected');
}

function archiveThenEncrypt() {
    alert('Archive then Encrypt operation selected');
}

function encryptThenArchive() {
    alert('Encrypt then Archive operation selected');
}

function writeToInputFile() {
    alert('Write to File operation selected');
}

function writeToOutputFile() {
    alert('Write to Output File operation selected');
}

function chooseInputFile() {
    const selectedFormat = document.getElementById('inputFormat').value;
    const inputFileElement = document.createElement('input');
    inputFileElement.type = 'file';
    inputFileElement.accept = `.${selectedFormat}, .zip, .rar`;

    inputFileElement.onchange = function() {
        inputFile = inputFileElement.files[0];
        document.getElementById('selectedInputFileLabel').textContent = inputFile.name;
    };

    inputFileElement.click();
}

function chooseOutputFile() {
    const selectedFormat = document.getElementById('outputFormat').value;
    const outputFileElement = document.createElement('input');
    outputFileElement.type = 'file';
    outputFileElement.accept = `.${selectedFormat}, .zip, .rar`;

    outputFileElement.onchange = function() {
        outputFile = outputFileElement.files[0];
        document.getElementById('selectedOutputFileLabel').textContent = outputFile.name;
    }

    outputFileElement.click();
}

function calculate() {
    var expressions = document.getElementById('inputTextArea').value.trim();

    if (!expressions) {
        alert("Please enter expressions to calculate.");
        return;
    }

    var payload = JSON.stringify({ expressions: expressions.split('\n') });

    // Создаем и отправляем AJAX запрос на сервер
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/calculate_expressions");
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            try {
                var results = JSON.parse(xhr.responseText);
                document.getElementById("outputTextArea").value = results.join('\n');
            } catch (e) {
                document.getElementById("outputTextArea").value = "Error parsing response.";
            }
        } else {
            document.getElementById("outputTextArea").value = "Error in response.";
        }
    };

    xhr.onerror = function () {
        document.getElementById("outputTextArea").value = "Network error.";
    };

    xhr.send(payload);
}

let selectedFile = null;
let filePaths = {};

function readFromInputFile() {
    const inputFormat = document.getElementById('inputFormat').value;
    const encryptionKey = document.getElementById('encryptionKeyInput').value;

    if (!inputFile) {
        alert("Please select a file.");
        console.error("File not selected.");
        return;
    }

    const fileExtension = inputFile.name.split('.').pop().toLowerCase();

    if (fileExtension === 'zip' || fileExtension === 'rar') {
        const payload = JSON.stringify({
            inputFormat: inputFormat,
            encryptionKey: encryptionKey,
            fileName: inputFile.name
        });

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/readFromInputFile");
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    const data = JSON.parse(xhr.responseText);

                    if (data.error) {
                        console.error("Error receiving data: ", data.error);
                        alert(data.error);
                    } else if (data.files) {
                        showModalWithFiles(data.files, inputFormat);
                    } else {
                        document.getElementById('inputTextArea').value = data.expressions.join("\n");
                    }
                } catch (e) {
                    console.error("Parsing error:", e);
                }
            } else {
                console.error("Request error: ", xhr.status);
            }
        };

        xhr.onerror = function() {
            console.error("Request error");
        };

        xhr.send(payload);
    } else {
        const reader = new FileReader();
        reader.onload = function(event) {
            const fileContent = event.target.result;
            const payload = JSON.stringify({
                inputFormat: inputFormat,
                encryptionKey: encryptionKey,
                fileContent: fileContent
            });

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/readFromInputFile");
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    try {
                        const data = JSON.parse(xhr.responseText);

                        if (data.error) {
                            console.error("Error receiving data: ", data.error);
                            alert(data.error);
                        } else if (data.files) {
                            showModalWithFiles(data.files, inputFormat);
                        } else {
                            document.getElementById('inputTextArea').value = data.expressions.join("\n");
                        }
                    } catch (e) {
                        console.error("Parsing error:", e);
                    }
                } else {
                    console.error("Request error: ", xhr.status);
                }
            };

            xhr.onerror = function() {
                console.error("Request error");
            };

            xhr.send(payload);
        };

        reader.onerror = function() {
            console.error("File reading error");
        };

        reader.readAsText(inputFile);
    }
}

function showModalWithFiles(files, inputFormat) {
    filePaths = {};
    const modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = files
        .filter(file => file.endsWith(`.${inputFormat}`)) // Фильтрация файлов по формату
        .map(file => {
            const fileName = file.split('/').pop(); // Извлечение имени файла без пути
            filePaths[fileName] = file; // Сохранение соответствия имени файла и его полного пути
            return `<div><input type="radio" name="file" value="${fileName}"> ${fileName}</div>`;
        })
        .join('');
    const modal = document.getElementById('modal');
    modal.style.display = "block";
}

function confirmFileSelection(textAreaId) {
    const selectedRadio = document.querySelector('input[name="file"]:checked');
    if (selectedRadio) {
        const selectedFileName = selectedRadio.value;
        selectedFile = filePaths[selectedFileName];

        const payload = JSON.stringify({
            filePath: selectedFile
        });

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/readFileContent");
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    const data = JSON.parse(xhr.responseText);
                    document.getElementById(textAreaId).value = data.fileContent;
                } catch (e) {
                    console.error("Parsing error:", e);
                }
            } else {
                console.error("Request error: ", xhr.status);
            }
        };

        xhr.onerror = function() {
            console.error("Request error");
        };

        xhr.send(payload);
        closeModal();
    } else {
        alert("Please select a file.");
    }
}

function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.display = "none";
}


function readFromOutputFile() {
    const outputFormat = document.getElementById('outputFormat').value;
    const encryptionKey = document.getElementById('encryptionKeyOutput').value;

    if (!outputFile) {
        alert("Please select a file.");
        console.error("File not selected.");
        return;
    }

    const fileExtension = outputFile.name.split('.').pop().toLowerCase();

    if (fileExtension === 'zip' || fileExtension === 'rar') {
        const payload = JSON.stringify({
            outputFormat: outputFormat,
            encryptionKey: encryptionKey,
            fileName: outputFile.name
        });

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/readFromOutputFile");
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    const data = JSON.parse(xhr.responseText);

                    if (data.error) {
                        console.error("Error receiving data: ", data.error);
                        alert(data.error);
                    } else if (data.files) {
                        showModalWithFiles(data.files, outputFormat);
                    } else {
                        document.getElementById('outputTextArea').value = data.expressions.join("\n");
                    }
                } catch (e) {
                    console.error("Parsing error:", e);
                }
            } else {
                console.error("Request error: ", xhr.status);
            }
        };

        xhr.onerror = function() {
            console.error("Request error");
        };

        xhr.send(payload);
    } else {
        const reader = new FileReader();
        reader.onload = function(event) {
            const fileContent = event.target.result;
            const payload = JSON.stringify({
                outputFormat: outputFormat,
                encryptionKey: encryptionKey,
                fileContent: fileContent
            });

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/readFromOutputFile");
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    try {
                        const data = JSON.parse(xhr.responseText);

                        if (data.error) {
                            console.error("Error receiving data: ", data.error);
                            alert(data.error);
                        } else if (data.files) {
                            showModalWithFiles(data.files, outputFormat);
                        } else {
                            document.getElementById('outputTextArea').value = data.expressions.join("\n");
                        }
                    } catch (e) {
                        console.error("Parsing error:", e);
                    }
                } else {
                    console.error("Request error: ", xhr.status);
                }
            };

            xhr.onerror = function() {
                console.error("Request error");
            };

            xhr.send(payload);
        };

        reader.onerror = function() {
            console.error("File reading error");
        };

        reader.readAsText(outputFile);
    }
}