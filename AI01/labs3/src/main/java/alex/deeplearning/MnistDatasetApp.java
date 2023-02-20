package alex.deeplearning;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;

/**
 * Handwritten digits recognition:
 * see <a href="https://en.wikipedia.org/wiki/MNIST_database">Dataset Wiki</a>
 */
public class MnistDatasetApp {
    public static void main(String[] args) throws IOException {
        int numOfChannels = 1;
        int numOfOutput = 10;
        int batchSize = 64;
        int numOfEpochs = 1;
        int seed = 123;
        DataSetIterator trainDs = new MnistDataSetIterator(batchSize, true, seed);
        DataSetIterator testDs = new MnistDataSetIterator(batchSize, false, seed);

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(seed)
                //.regularization(true)
                .l2(0.0005)
                .updater(new Nesterovs())
                //.updater(Updater.NESTEROVS)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                //the filter (feature detector) 5x5
                .layer(0, new ConvolutionLayer.Builder(5, 5)
                        .nIn(numOfChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.RELU)
                        .build()
                )
                //pooling layer AKA subsampling layer with MAX pooling
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build()
                )
                // convolutional layers + pooling layers right after each other
                .layer(2, new ConvolutionLayer.Builder(5, 5)
                        .stride(1, 1)
                        .nOut(50)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                //we use densly connected network
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(500).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(numOfOutput)
                        .activation(Activation.SOFTMAX)
                        .build())
                //MNIST has 60k samples with 28x28x1 grayscale images
                .setInputType(InputType.convolutionalFlat(28, 28, 1))
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
        neuralNetwork.init();

        neuralNetwork.setListeners(new ScoreIterationListener(1));

        //60K samples / batchSize - gives 937 iterations
        for (int i = 0; i < numOfEpochs; i++) {
            neuralNetwork.fit(trainDs);
            var evaluation = neuralNetwork.evaluate(testDs);
            System.out.println(evaluation.stats());
            testDs.reset();
        }

    }
}
