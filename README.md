# PID Tuner

### Overview:
**PID Tuner** is a app for computing Proportional-Integral-Derivative (PID) controller parameters based on various established tuning methods. Designed to help engineers and control systems professionals to fine-tune PID controller, the app provides intuitive tools for selecting and applying different tuning algorithms based on system characteristics and desired performance.
This tool supports tuning methods such as

- Cohen-Coon: Optimized for systems with time delay.
- Chien-Hrones-Reswick: Focused on setpoint tracking or disturbance rejection.
- Integral Absolute Error (IAE): Smooth control with minimal accumulated error.
- Integral Time Absolute Error (ITAE): Reduced long-term error and oscillations.
- Internal Model Control (IMC): Systematic tuningModel with a process model.
- Ziegler-Nichols: Aggressive, fast response with potential oscillations.
- Tyreus-Luyben: Balanced tuning model for slower systems with less aggressive behavior.

### Transfer Function
For a first-order process with dead time, the system model can be represented as:

$$G(S) = \frac{Kp * e^{-\theta s}} {\tau S + 1}$$

Where:
- Kp: Process Gain
- $\theta$: Transport Delay (Dead Time)
- $\tau$: Process Time Constant

PID Tuner requires only Kp, $\theta$ and $\tau$ parameters to compute Kp, Ki and Kd for PID controller. Only IMC methods requires one more parameter, the Lambda $\lambda$.

## Available Tuning Methods

The following methods are available for computing the controller parameters:

### 1. Ziegler-Nichols (Z&N)
Ziegler-Nichols provides open-loop and closed-loop tuningModel formulas. It aims to achieve quarter-wave damping in systems.


#### Open-Loop Tuning:
This method involves applying a step change to the process input and recording the output response. The key parameters, like process gain, time constant, and time delay, are extracted from the step response curve. These parameters are then used to calculate PID settings. It's best suited for processes with a smooth, S-shaped response.

|    Z&amp;N     | Kp                            |Ki                           | Kd           |
|----------------|-------------------------------|-----------------------------|-             |
|P               |$$\frac{\tau} {K*\theta}$$     |-                            |-             |
|PI              |$$\frac{0.9\tau} {K*\theta}$$  |$$3.33\theta$$               |-             |
|PID             |$$\frac{1.2\tau} {K*\theta}$$  |$$2\theta$$                 |$$0.5\theta$$  |

#### Closed-Loop Tuning:
In the closed-loop method, the system is driven into sustained oscillations by gradually increasing the proportional gain (Kp) while setting the integral (Ki) and derivative (Kd) gains to zero. The gain at which the system oscillates consistently is called the ultimate gain (Ku), and the period of the oscillations is the ultimate period (Pu). These values are used to calculate the PID parameters for fast, aggressive response tuningModel.

|    Z&amp;N     | Kp            |Ki                           |  Kd             |
|----------------|---------------|-----------------------------|-----------------|
|P               |$$0.5Ku$$     |-                            |-                |
|PI              |$$0.45Ku$$    |$$\frac{Pu} {1.2}$$          |-                |
|PID             |$$0.6Ku$$   |$$\frac{Pu} {2}$$            |$$\frac{Pu} {8}$$|

Where Ku is the ultimate gain and Pu is the oscillation period.

### 2. Chien, Hrones, and Reswick (CHR)

The Chien-Hrones-Reswick (CHR) tuningModel method is a technique designed to optimize PID controller parameters for specific system behaviors, focusing on either setpoint tracking or disturbance rejection. It provides different tuningModel rules based on the desired control objectives, making it adaptable to various performance requirements. It has separate tuningModel formulas for setpoint tracking (servo) and disturbance rejection (regulator) with or without overshoot (UP).

#### Servo:

|    CHR         | Kp                           |Ki            |  Kd                |
|----------------|------------------------------|--------------|--------------------|
|P               |$$\frac{0.3\tau}{K*\theta}$$  |-             |-                   |
|PI              |$$\frac{0.35\tau}{K*\theta}$$ |$$1.16\tau$$  |-                   |
|PID             |$$\frac{0.6\tau}{K*\theta}$$  |$$\tau$$      |$$\frac{\theta}{2}$$|

#### Servo with 20% Overshoot:

|    CHR         | Kp                           |Ki            |  Kd                |
|----------------|------------------------------|--------------|--------------------|
|P               |$$\frac{0.7\tau}{K*\theta}$$  |-             |-                   |
|PI              |$$\frac{0.6\tau}{K*\theta}$$  |$$\tau$$      |-                   |
|PID             |$$\frac{0.95\tau}{K*\theta}$$ |$$1.357\tau$$ |$$0.473\theta$$     |

#### Regulator:

|    CHR         | Kp                           |Ki              |  Kd                |
|----------------|------------------------------|----------------|--------------------|
|P               |$$\frac{0.3\tau}{K*\theta}$$  |-               |-                   |
|PI              |$$\frac{0.6\tau}{K*\theta}$$  |$$4\tau$$       |-                   |
|PID             |$$\frac{0.95\tau}{K*\theta}$$ |$$2.357\theta$$ |$$0.421\theta$$     |

#### Regulator with 20% Overshoot:

|    CHR         | Kp                           |Ki              |  Kd                |
|----------------|------------------------------|----------------|--------------------|
|P               |$$\frac{0.7\tau}{\tau}$$      |-               |-                   |
|PI              |$$\frac{0.7\tau}{\theta}$$    |$$2.3\theta$$   |-                   |
|PID             |$$\frac{1.2\tau}{\theta}$$    |$$2\theta$$     |$$0.421\theta$$     |

### 3. Cohen-Coon (CC)

The Cohen-Coon (CC) tuningModel method is a widely used technique for PID controller tuningModel, particularly for first-order systems with time delay. It provides formulas to calculate the PID parameters based on the system's dynamic response. The CC method improves both setpoint tracking and disturbance rejection, making it suitable for systems with significant dead time.

|    Z&amp;N     | Kp                                                         |Ki   | Kd|
|----------------|------------------------------------------------------------|-----|---|
|P               |$$(1.03+0.35*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|-    |-  |
|PI              |$$(0.9+0.083*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|  $$\frac{0.9+0.083*(\frac{\theta}			{\tau})}{1.27 + 0.6*(\frac{\theta}{\tau})}*\theta$$   |-  |
|PD              |$$\frac{1.24}{K}*(\frac{\tau}{\theta}+0.129)$$              |-    | $$0.27*\theta*(\frac{\tau-0.324*\theta}{\tau+0.129*\theta})$$  |
|PID             |$$(1.35+0.25*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|$$\frac{1.35+0.25*(\frac{\theta}{\tau})}{0.54+0.33*(\frac{\theta}{\tau})}*\theta$$    		|$$\frac{0.5*\theta}{1.35+0.25*(\frac{\theta}{\tau})}$$  	|

### 4. Internal Model Control (IMC)

The Internal Model Control (IMC) tuningModel method is a model-based approach to PID tuningModel, where the controller is designed based on an internal model of the process. The primary goal of IMC is to provide robust and stable control, even in the presence of process uncertainties and disturbances. It is commonly used in industrial applications due to its simplicity and effectiveness.


|    IMC         | Kp            |Ki                           |  Kd |~  |
|----------------|---------------|-----------------------------|-----|- |
|PI              |$$\frac{2\tau+\theta}{K*2\lambda}$$        |$$\tau + (\frac{\theta}{\tau})$$    |- |$$\frac{\lambda}	{\theta}>1.7$$|
|PID             |$$\frac{2\tau+\theta}{K*(2\lambda+\theta)}$$   |$$\tau + (\frac{\theta}{\tau})$$            	|$$\frac{\tau*\theta}{2\tau+\theta}$$|$$\frac{\lambda}{\theta}>0.8$$|

**The IMC Method in PID Tuner is based on technique proposed by Morari and Zafiriou in \'Robust Process Control\' of PID tunings for first order plus dead time function.**

#### Lambda Tuning
In Internal Model Control (IMC) tuningModel, the lambda (λ) parameter plays a crucial role in determining the trade-off between system performance and robustness. Lambda is the filter time constant, and it directly affects the tuningModel of the PID controller.

- Role of Lambda (λ):
	- Lambda represents the desired closed-loop time constant, which controls the speed of the system’s response.
 	- A smaller λ results in a faster response but less robustness (potentially more aggressive control, with possible overshoot or oscillations).
  	- A larger λ results in a slower response but more robustness (the system will be more stable and less sensitive to disturbances or model inaccuracies).
- Choosing Lambda:
Lambda is typically chosen by the control engineer based on the desired level of robustness and performance. In general:
	- For fast response systems, choose a small λ (close to the process dead time, 𝜃).
	- For more stable or robust systems, choose a larger λ (2 to 3 times the dead time, 𝜃).

### 5. IAE / ITAE

**Integral Absolute Error (IAE)** tuningModel method focuses on minimizing the total absolute error over time. It is commonly used in control systems where smooth, long-term performance is prioritized over fast response times. IAE tuningModel emphasizes reducing the accumulated error, leading to a more gradual and stable response.

**The Integral Time Absolute Error (ITAE)** tuningModel method focuses on minimizing the time-weighted absolute error between the process variable and the setpoint. ITAE adds more emphasis on errors that persist over time, resulting in smoother control actions and reduced overshoot and oscillations. It's particularly useful in systems where long-term performance and stability are more important than short-term speed.

#### Servo:

$$
Kp = \frac{1}{K} \cdot \left(A \cdot \left(\frac{\theta}{\tau}\right)^B\right)
$$

$$
Ki = \frac{\tau}{(C + D * \frac{\theta}{\tau})}
$$ 

$$
Kd = E*(\frac{\theta}{\tau})^F
$$ 

> Where A, B, C, D, E and F are parameters from Table IAE/ITAE Servo.

IAE/ITAE Servo:

|Controller  |Method   |A    |B     |C    |D     |E    |F    |
|------------|---------|-----|------|-----|------|-----|-----|
|PI          |IAE      |0.758|-0.861|1.02 |-0.323|-    |-    |
|PID         |IAE      |1.086|-0.869|0.740|-0.130|0.348|0.914|
|PI          |ITAE     |0.586|-0.916|1.03 |-0.165|-    |-    |
|PID         |ITAE     |0.965|-0.850|0.796|-0.147|0.308|0.929|

#### Regulator:

$$
Kp = \frac{1}{K} \cdot \left( A \cdot \left( \frac{\theta}{\tau} \right)^B \right)
$$

$$
Ki = \frac{\tau}{(C * (\frac{\theta}{\tau})^D)}
$$ 

$$
Kd = E*(\frac{\theta}{\tau})^F
$$ 

> Where A, B, C, D, E and F are parameters from Table IAE/ITAE Regulator.

IAE/ITAE Regulator:

|Controller  |Method   |A    |B     |C    |D     |E    |F    |
|------------|---------|-----|------|-----|------|-----|-----|
|PI          |IAE      |0.984|-0.986|0.608|-0.707|-    |-    |
|PID         |IAE      |1.435|-0.921|0.878|-0.749|0.482|1.137|
|PI          |ITAE     |0.859|-0.977|0.674|-0.68 |-    |-    |
|PID         |ITAE     |1.357|-0.947|0.842|-0.738|0.381|0.995|

### 6. Tyreus-Luyben (TL)

Tyreus-Luyben is a conservative method offering slower performance and small overshoot, especially for critical systems.

|    Z&amp;N     | Kp                 |Ki                           |  Kd               |
|----------------|--------------------|-----------------------------|-------------------|
|P               |-                   |-                            |-                  |
|PI              |$$\frac{Ku} {3.2}$$ |$$\frac{Pu} {0.45}$$         |-                  |
|PID             |$$\frac{Ku} {3.2}$$ |$$\frac{Pu} {0.45}$$         |$$\frac{Pu} {6.3}$$|

> Where Ku and Pu are, respectively., ultimate gain e ultimate period.

# References
- Ziegler, J.G., & Nichols, N.B. (1942). Optimum settings for automatic controllers. Transactions of the ASME, 64(11), 759-768.
- Chien, I., Hrones, J.A., & Reswick, J.B. (1952). On the Automatic Control of Generalized Passive Systems. Transactions of the ASME, 74, 175-185.
- Cohen, G.H., & Coon, G.A. (1953). Theoretical Consideration of Retarded Control. ASME Transactions, 75(1), 827-834.
- Rivera, D.E., Morari, M., & Skogestad, S. (1986). Internal Model Control: PID Controller Design. Industrial & Engineering Chemistry Process Design and Development, 25(1), 252-265.
- Tyreus, B.D., & Luyben, W.L. (1992). Tuning PI Controllers for Integrator/Dead Time Processes. Industrial & Engineering Chemistry Research, 31(11), 2625-2628.
- Morari, M., & Zafiriou, E. (1989). Robust Process Control. Prentice Hall.
- Åström, K.J., & Hägglund, T. (1995). PID Controllers: Theory, Design, and Tuning. Instrument Society of America.
- Campos, M.C.M., & Teixeira, H.C.G. (2010). Controles Típicos de equipamentos e processos industriais. 2ª ed. Editora Blucher.
