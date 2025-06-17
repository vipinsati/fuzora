// import JSONSchemaForm from '../forms/page'

import { Button, Step, StepLabel, Stepper, TextField } from "@mui/material";
import Box from "@mui/material/Box";
import * as React from 'react';
import TriggerDetailsForm from "./TriggerDetailsForm";



const steps = ['Flow Name', 'Trigger Configuration', 'Filter', 'Transformer', 'Action'];

export default function NewIntegrationView() {

  const [activeStep, setActiveStep] = React.useState(0);

  const handleNext = () => setActiveStep((prev) => prev + 1);
  const handleBack = () => setActiveStep((prev) => prev - 1);

  const renderStepContent = (step: number) => {
    switch (step) {
      case 0:
        return <div>
          <TextField id="standard-basic" label="Enter Flow Name" variant="standard" />
        </div>;
      case 1:
        return <TriggerDetailsForm />;
      case 2:
        return <div>Enter filter configuration</div>;
      case 3:
        return <div>Enter transformer configuration</div>;
      case 4:
        return <div>Enter action configuration</div>;

      default:
        return <div>Unknown step</div>;
    }
  };

  return (
    <div>
      <h1 className="text-xl font-bold pb-5">New Integration</h1>
      {/* <Divider variant="middle" /> */}
      <Box>
        <Stepper activeStep={activeStep} alternativeLabel>
          {
            steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))
          }
        </Stepper>
        <div className="mt-4">{renderStepContent(activeStep)}</div>
        <div className="mt-4 flex justify-end gap-4 p-4">
          <Button disabled={activeStep === 0} variant="contained" onClick={handleBack}>Back</Button>
          <Button variant="contained" onClick={handleNext}>{activeStep === steps.length - 1 ? 'Finish' : 'Next'}</Button>
        </div>
      </Box>
      {/* <JSONSchemaForm /> */}
    </div>
  );
}