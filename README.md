# PID Tuner

**PID Tuner** gathers several PID controller tuning methods. Z&amp;N, CHR, CC, IMC, IAE... methods are available to calculate the controller parameters (Proportional, Integral and Derivative) using the process parameters  (Gain, Time Constant and Dead Time).

Given the following first order function with dead time (transport delay), it`s possible to retrieve some information to compute PID parameters.
$$G(S) = \frac{Kp * e^{-\theta s}} {\tau S + 1}$$

> Where Kp (K) is the Process Gain; $\theta$ is the Transport Delay and $\tau$ is the Process Time Constant.

PID Tuner requires only Kp, $\theta$ and $\tau$ parameters to compute Kp, Ki and Kd for PID controller. Only IMC methods requires one more parameter, the Lambda $\lambda$.

## Available Tuning Methods
 The following methods are available for computing the controller parameters:

- ###  Z&amp;N

Ziegler-Nichols (Z&amp;N) is a PID tuning method in which two methods are proposed to obtain a model of the dynamics of a SISO process, analyzing it in closed-loop and open-loop.

The following table shows the equations for each PID parameter:

- Open-Loop:

|    Z&amp;N     | Kp                            |Ki                           | Kd           |
|----------------|-------------------------------|-----------------------------|-             |
|P               |$$\frac{\tau} {K*\theta}$$     |-                            |-             |
|PI              |$$\frac{0.9\tau} {K*\theta}$$  |$$3.33\theta$$               |-             |
|PID             |$$\frac{1.2\tau} {K*\theta}$$  |$$2\theta$$                 |$$0.5\theta$$ |

- Closed-Loop:

|    Z&amp;N     | Kp            |Ki                           |  Kd             |
|----------------|---------------|-----------------------------|-----------------|
|P               |$$0.5Ku$$     |-                            |-                |
|PI              |$$0.45Ku$$    |$$\frac{Pu} {1.2}$$          |-                |
|PID             |$$0.6Ku$$   |$$\frac{Pu} {2}$$            |$$\frac{Pu} {8}$$|

- ### CHR

Chien, Hrones and Reswick (CHR) is a tuning method that proposes two performance criteria: the fastest possible response without overshoot and the fastest possible response with 20% overshoot.

The following table shows the equations for each PID parameter:

- Servo:

|    CHR         | Kp                           |Ki            |  Kd                |
|----------------|------------------------------|--------------|--------------------|
|P               |$$\frac{0.3\tau}{K*\theta}$$  |-             |-                   |
|PI              |$$\frac{0.35\tau}{K*\theta}$$ |$$1.16\tau$$  |-                   |
|PID             |$$\frac{0.6\tau}{K*\theta}$$  |$$\tau$$      |$$\frac{\theta}{2}$$|

- Servo with 20% Overshoot:

|    CHR         | Kp                           |Ki            |  Kd                |
|----------------|------------------------------|--------------|--------------------|
|P               |$$\frac{0.7\tau}{K*\theta}$$  |-             |-                   |
|PI              |$$\frac{0.6\tau}{K*\theta}$$  |$$\tau$$      |-                   |
|PID             |$$\frac{0.95\tau}{K*\theta}$$ |$$1.357\tau$$ |$$0.473\theta$$     |

- Regulation:

|    CHR         | Kp                           |Ki              |  Kd                |
|----------------|------------------------------|----------------|--------------------|
|P               |$$\frac{0.3\tau}{K*\theta}$$  |-               |-                   |
|PI              |$$\frac{0.6\tau}{K*\theta}$$  |$$4\tau$$       |-                   |
|PID             |$$\frac{0.95\tau}{K*\theta}$$ |$$2.357\theta$$ |$$0.421\theta$$     |

- Regulation with 20% Overshoot:

|    CHR         | Kp                           |Ki              |  Kd                |
|----------------|------------------------------|----------------|--------------------|
|P               |$$\frac{0.7\tau}{\tau}$$      |-               |-                   |
|PI              |$$\frac{0.7\tau}{\theta}$$    |$$2.3\theta$$   |-                   |
|PID             |$$\frac{1.2\tau}{\theta}$$    |$$2\theta$$     |$$0.421\theta$$     |

- ### CC

Cohen-Coon (CC) is a method for tuning pid controller for higher dead time processes.

The following table shows the equations for each PID parameter:

|    Z&amp;N     | Kp                                                         |Ki   | Kd|
|----------------|------------------------------------------------------------|-----|---|
|P               |$$(1.03+0.35*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|-    |-  |
|PI              |$$(0.9+0.083*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|  $$\frac{0.9+0.083*(\frac{\theta}{\tau})}{1.27 + 0.6*(\frac{\theta}{\tau})}*\theta$$   |-  |
|PD              |$$\frac{1.24}{K}*(\frac{\tau}{\theta}+0.129)$$              |-    | $$0.27*\theta*(\frac{\tau-0.324*\theta}{\tau+0.129*\theta})$$  |
|PID             |$$(1.35+0.25*(\frac{\theta}{\tau}))*\frac{\tau}{K *\theta}$$|$$\frac{1.35+0.25*(\frac{\theta}{\tau})}{0.54+0.33*(\frac{\theta}{\tau})}*\theta$$    |$$\frac{0.5*\theta}{1.35+0.25*(\frac{\theta}{\tau})}$$  |

- ### IMC

Internal Model Control (IMC) is a PID tuning method that aims to tune in such a way that the system response has a known dynamics and for that it uses a process model and a performance specification (lambda tuning) for the adjustment.

The following table shows the equations for each PID parameter:

|    IMC         | Kp            |Ki                           |  Kd |~  |
|----------------|---------------|-----------------------------|-----|- |
|PI              |$$\frac{2\tau+\theta}{K*2\lambda}$$        |$$\tau + (\frac{\theta}{\tau})$$    |- |$$\frac{\lambda}{\theta}>1.7$$|
|PID             |$$\frac{2\tau+\theta}{K*(2\lambda+\theta)}$$   |$$\tau + (\frac{\theta}{\tau})$$            |$$\frac{\tau*\theta}{2\tau+\theta}$$|$$\frac{\lambda}{\theta}>0.8$$|

- ### IAE / ITAE

**Integral Absolute Error (IAE)** is a tuning method proposed to eliminate the error in steady state integrating the absolute error over time. **Integral Time Absolute Error (ITAE)** is a tuning method proposed to eliminate the error in steady state using as an performance criterion the integrates the absolute error multiplied by the time over time.

The following table shows the equations for each PID parameter:

- Servo:

$$Kp = \frac{1}{K}*(A*(\frac{\theta}{\tau})^B)$$ 
$$Ki = \frac{\tau}{(C + D * \frac{\theta}{\tau})}$$ 
$$Kd = E*(\frac{\theta}{\tau})^F$$ 

> Where A, B, C, D, E and F are parameters from Table IAE/ITAE Servo.

|Controller  |Method   |A    |B     |C    |D     |E    |F    |
|------------|---------|-----|------|-----|------|-----|-----|
|PI          |IAE      |0.758|-0.861|1.02 |-0.323|-    |-    |
|PID         |IAE      |1.086|-0.869|0.740|-0.130|0.348|0.914|
|PI          |ITAE     |0.586|-0.916|1.03 |-0.165|-    |-    |
|PID         |ITAE     |0.965|-0.850|0.796|-0.147|0.308|0.929|

- Regulator:

$$Kp = \frac{1}{K}*(A*(\frac{\theta}{\tau})^B)$$ 
$$Ki = \frac{\tau}{(C * (\frac{\theta}{\tau})^D)}$$ 
$$Kd = E*(\frac{\theta}{\tau})^F$$ 

> Where A, B, C, D, E and F are parameters from Table IAE/ITAE Regulator.

|Controller  |Method   |A    |B     |C    |D     |E    |F    |
|------------|---------|-----|------|-----|------|-----|-----|
|PI          |IAE      |0.984|-0.986|0.608|-0.707|-    |-    |
|PID         |IAE      |1.435|-0.921|0.878|-0.749|0.482|1.137|
|PI          |ITAE     |0.859|-0.977|0.674|-0.68 |-    |-    |
|PID         |ITAE     |1.357|-0.947|0.842|-0.738|0.381|0.995|

- ### Tyreus-Luyben

Tyreus-Luyben (TL) is a more conservative PID adjustment method than that proposed by Ziegler-Nichols, as it consists of using the gain and the critical period for the adjustment, providing slow performance and small overshoot.

The following table shows the equations for each PID parameter:

|    Z&amp;N     | Kp                 |Ki                           |  Kd               |
|----------------|--------------------|-----------------------------|-------------------|
|P               |-                   |-                            |-                  |
|PI              |$$\frac{Ku} {3.2}$$ |$$\frac{Pu} {0.45}$$         |-                  |
|PID             |$$\frac{Ku} {3.2}$$ |$$\frac{Pu} {0.45}$$         |$$\frac{Pu} {6.3}$$|