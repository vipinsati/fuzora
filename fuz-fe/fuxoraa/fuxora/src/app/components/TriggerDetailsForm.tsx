import React, { useState } from 'react';
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Button,
  Typography,
} from '@mui/material';

export default function TriggerDetailsForm() {
  const [appName, setAppName] = useState('');
  const [triggerType, setTriggerType] = useState('');
  const [authType, setAuthType] = useState('');
  const [params, setParams] = useState({});
  const [samplePayload, setSamplePayload] = useState('');

  const renderAuthFields = () => {
    switch (authType) {
      case 'OAuth':
        return (
          <div className="space-y-4">
            <TextField fullWidth label="Scope" onChange={(e) => setParams({ ...params, scope: e.target.value })} />
            <TextField fullWidth label="Auth URL" onChange={(e) => setParams({ ...params, url: e.target.value })} />
            <TextField fullWidth label="Access Token" onChange={(e) => setParams({ ...params, access_token: e.target.value })} />
            <TextField fullWidth label="Refresh Token" onChange={(e) => setParams({ ...params, refresh_token: e.target.value })} />
          </div>
        );
      case 'BasicAuth':
        return (
          <div className="space-y-4">
            <TextField fullWidth label="URL" onChange={(e) => setParams({ ...params, url: e.target.value })} />
            <TextField fullWidth label="Username" onChange={(e) => setParams({ ...params, username: e.target.value })} />
            <TextField fullWidth label="Password" type="password" onChange={(e) => setParams({ ...params, password: e.target.value })} />
          </div>
        );
      case 'APIKey':
        return (
          <div className="space-y-4">
            <TextField fullWidth label="URL" onChange={(e) => setParams({ ...params, url: e.target.value })} />
            <TextField fullWidth label="API Key" onChange={(e) => setParams({ ...params, apiKey: e.target.value })} />
          </div>
        );
      default:
        return null;
    }
  };

  const renderConnectionFields = () => {
    switch (triggerType) {
      case 'HTTP':
        return (
          <FormControl fullWidth className="mt-4">
            <InputLabel>Auth Type</InputLabel>
            <Select value={authType} label="Auth Type" onChange={(e) => setAuthType(e.target.value)}>
              <MenuItem value="OAuth">OAuth</MenuItem>
              <MenuItem value="BasicAuth">Basic Auth</MenuItem>
              <MenuItem value="APIKey">API Key</MenuItem>
            </Select>
            <div className="mt-4">{renderAuthFields()}</div>
          </FormControl>
        );
      case 'SFTP':
        return (
          <div className="space-y-4 mt-4">
            <TextField fullWidth label="Host" onChange={(e) => setParams({ ...params, host: e.target.value })} />
            <TextField fullWidth label="Port" onChange={(e) => setParams({ ...params, port: e.target.value })} />
            <TextField fullWidth label="Username" onChange={(e) => setParams({ ...params, username: e.target.value })} />
            <TextField fullWidth label="Password" type="password" onChange={(e) => setParams({ ...params, password: e.target.value })} />
          </div>
        );
      case 'RabbitMQ':
        return (
          <div className="space-y-4 mt-4">
            <TextField fullWidth label="Host" onChange={(e) => setParams({ ...params, host: e.target.value })} />
            <TextField fullWidth label="Port" onChange={(e) => setParams({ ...params, port: e.target.value })} />
            <TextField fullWidth label="Username" onChange={(e) => setParams({ ...params, username: e.target.value })} />
            <TextField fullWidth label="Password" type="password" onChange={(e) => setParams({ ...params, password: e.target.value })} />
            <TextField fullWidth label="Virtual Host" onChange={(e) => setParams({ ...params, virtual_host: e.target.value })} />
            <TextField fullWidth label="Exchange" onChange={(e) => setParams({ ...params, exchange: e.target.value })} />
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6 bg-white rounded shadow-md">
      <Typography variant="h6" gutterBottom>
        Trigger Details
      </Typography>
      <div className="space-y-6">
        <TextField
          fullWidth
          label="App Name"
          value={appName}
          onChange={(e) => setAppName(e.target.value)}
        />

        <FormControl fullWidth>
          <InputLabel>Trigger Type</InputLabel>
          <Select
            value={triggerType}
            label="Trigger Type"
            onChange={(e) => {
              setTriggerType(e.target.value);
              setAuthType('');
              setParams({});
            }}
          >
            <MenuItem value="HTTP">HTTP</MenuItem>
            <MenuItem value="SFTP">SFTP</MenuItem>
            <MenuItem value="RabbitMQ">RabbitMQ</MenuItem>
          </Select>
        </FormControl>

        {renderConnectionFields()}

        <TextField
          fullWidth
          multiline
          minRows={4}
          label="Optional: Sample Payload or Keys"
          value={samplePayload}
          onChange={(e) => setSamplePayload(e.target.value)}
        />

        <Button variant="contained" color="primary" onClick={() => console.log({ appName, triggerType, authType, params, samplePayload })}>
          Submit
        </Button>
      </div>
    </div>
  );
}

// 'use client';

// import React, { useState } from 'react';
// import {
//   TextField,
//   FormControl,
//   InputLabel,
//   Select,
//   MenuItem,
//   Typography,
//   Box,
//   Button
// } from '@mui/material';

// const TriggerDetailsForm = () => {
//   const [triggerType, setTriggerType] = useState('');
//   const [authType, setAuthType] = useState('');
//   const [formValues, setFormValues] = useState({
//     appName: '',
//     url: '',
//     username: '',
//     password: '',
//     apiKey: '',
//     scope: '',
//     accessToken: '',
//     refreshToken: '',
//     host: '',
//     port: '',
//     virtualHost: '',
//     exchange: '',
//     samplePayload: '',
//   });

//   const handleChange = (e: any) => {
//     setFormValues({ ...formValues, [e.target.name]: e.target.value });
//   };

//   return (
//     <Box className="p-6 space-y-6 bg-white shadow rounded-xl max-w-2xl mx-auto">
      
//       {/* Section 1 */}
//       <Typography variant="h6" className="font-bold border-b pb-2">1. App Information</Typography>
//       <FormControl fullWidth className="mb-4">
//         <TextField
//           label="Enter App Name"
//           name="appName"
//           value={formValues.appName}
//           onChange={handleChange}
//         />
//       </FormControl>

//       {/* Section 2 */}
//       <Typography variant="h6" className="font-bold border-b pb-2">2. Trigger Type</Typography>
//       <FormControl fullWidth className="mb-4">
//         <InputLabel>Select Trigger Type</InputLabel>
//         <Select
//           value={triggerType}
//           onChange={(e) => {
//             setTriggerType(e.target.value);
//             setAuthType('');
//           }}
//         >
//           <MenuItem value="HTTP">HTTP</MenuItem>
//           <MenuItem value="SFTP">SFTP</MenuItem>
//           <MenuItem value="RabbitMQ">RabbitMQ</MenuItem>
//         </Select>
//       </FormControl>

//       {/* Section 3 */}
//       <Typography variant="h6" className="font-bold border-b pb-2">3. Connection Parameters</Typography>
//       {triggerType === 'HTTP' && (
//         <>
//           <FormControl fullWidth className="mb-4">
//             <InputLabel>Select Auth Type</InputLabel>
//             <Select value={authType} onChange={(e) => setAuthType(e.target.value)}>
//               <MenuItem value="OAuth">OAuth</MenuItem>
//               <MenuItem value="BasicAuth">Basic Auth</MenuItem>
//               <MenuItem value="APIKey">API Key</MenuItem>
//             </Select>
//           </FormControl>

//           {authType === 'OAuth' && (
//             <>
//               <TextField label="Auth URL" name="url" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="Scope" name="scope" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="Access Token" name="accessToken" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="Refresh Token" name="refreshToken" onChange={handleChange} fullWidth />
//             </>
//           )}
//           {authType === 'BasicAuth' && (
//             <>
//               <TextField label="URL" name="url" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="Username" name="username" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="Password" name="password" type="password" onChange={handleChange} fullWidth />
//             </>
//           )}
//           {authType === 'APIKey' && (
//             <>
//               <TextField label="URL" name="url" onChange={handleChange} fullWidth className="mb-2" />
//               <TextField label="API Key" name="apiKey" onChange={handleChange} fullWidth />
//             </>
//           )}
//         </>
//       )}

//       {triggerType === 'SFTP' && (
//         <>
//           <TextField label="Host" name="host" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Port" name="port" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Username" name="username" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Password" name="password" type="password" onChange={handleChange} fullWidth />
//         </>
//       )}

//       {triggerType === 'RabbitMQ' && (
//         <>
//           <TextField label="Host" name="host" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Port" name="port" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Username" name="username" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Password" name="password" type="password" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Virtual Host" name="virtualHost" onChange={handleChange} fullWidth className="mb-2" />
//           <TextField label="Exchange" name="exchange" onChange={handleChange} fullWidth />
//         </>
//       )}

//       {/* Section 4 */}
//       <Typography variant="h6" className="font-bold border-b pb-2">4. Sample Payload (Optional)</Typography>
//       <TextField
//         label="Enter Sample Payload or Keys (optional)"
//         name="samplePayload"
//         multiline
//         minRows={4}
//         onChange={handleChange}
//         fullWidth
//         placeholder='{"event": "value", "key": "value"}'
//       />

//       <div className="pt-4">
//         <Button variant="contained" color="primary">Next</Button>
//       </div>
//     </Box>
//   );
// };

// export default TriggerDetailsForm;
