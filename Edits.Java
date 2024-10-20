<!DOCTYPE html>
<html>
<head>
    <title>Mold Detector</title>
    <!-- Load TensorFlow.js library -->
    <script src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs"></script>
    <!-- Load the Teachable Machine library -->
    <script src="https://cdn.jsdelivr.net/npm/@teachablemachine/image@0.8.4/dist/teachablemachine-image.min.js"></script>
</head>
<body>
    <h1>Mold Detector</h1>
    
    <!-- Button to start the prediction -->
    <button onclick="startPrediction()">Take Picture and Predict</button>

    <!-- Display prediction result -->
    <div id="predictionResult"></div>

    <script>
        let model;
        const URL = "https://teachablemachine.withgoogle.com/models/JbNhrDeuF/"; // URL to your model folder

        // Load the model
        async function loadModel() {
            try {
                model = await tmImage.load(`${URL}model.json`);
                console.log("Model loaded successfully!");
            } catch (error) {
                console.error("Error loading model:", error);
                document.getElementById("predictionResult").innerText = "Error loading model. Please refresh and try again.";
            }
        }

        // Function to receive the image from Thunkable
        function receiveImage(imageURL) {
            const imageElement = document.createElement('img');
            imageElement.src = imageURL;

            imageElement.onload = async function() {
                try {
                    const prediction = await model.predict(imageElement);
                    console.log("Prediction successful:", prediction);
                    document.getElementById("predictionResult").innerText = 
                        `Prediction: ${prediction[0].className} with ${Math.round(prediction[0].probability * 100)}% confidence`;
                } catch (error) {
                    console.error("Prediction failed:", error);
                    document.getElementById("predictionResult").innerText = "Prediction failed. Please try again.";
                }
            };

            imageElement.onerror = function() {
                console.error("Image failed to load:", imageURL);
                document.getElementById("predictionResult").innerText = "Image failed to load. Please check the URL.";
            };
        }

        // Call this function when you want to start the prediction process
        function startPrediction() {
            // Here, you should call the Thunkable function that captures the image
            // For example:
            // call Web_Viewer1.EvaluateJavaScript with "receiveImage('" + app variable imageURL + "')"
            console.log("Starting prediction...");
        }

        // Load model when the page starts
        window.onload = function() {
            loadModel();
        };
    </script>
</body>
</html>
