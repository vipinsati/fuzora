'use client';

import { JsonForms } from '@jsonforms/react';
import {
  materialRenderers,
  materialCells
} from '@jsonforms/material-renderers';
import { useState } from 'react';
import { Button } from '@mui/material';

// Example schema and uischema
const schema = {
  type: 'object',
  properties: {
    name: { type: 'string' },
    age: { type: 'number' },
  },
  required: ['name']
};

const uischema = {
  type: 'VerticalLayout',
  elements: [
    { type: 'Control', scope: '#/properties/name' },
    { type: 'Control', scope: '#/properties/age' }
  ]
};

export default function JsonSchemaForm() {
  const [data, setData] = useState({});

  return (
    <div className="bg-white p-4 rounded shadow-md">
      <JsonForms
        schema={schema}
        uischema={uischema}
        data={data}
        renderers={materialRenderers}
        cells={materialCells}
        onChange={({ data }) => setData(data)}
      />
      <Button
        className="mt-4"
        variant="contained"
        onClick={() => console.log('Submitted data:', data)}
      >
        Submit
      </Button>
    </div>
  );
}