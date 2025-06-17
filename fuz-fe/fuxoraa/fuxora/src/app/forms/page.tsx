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
    flow_name: { type: 'string', minLength: 3, description: 'Enter flow name' },
    app_name: { type: 'string' },
    app_type: {
      type: 'string',
      enum: ['HTTP', 'SFTP', 'AMQP']
    },
    connection_type: {
      type: 'string',
      enum: ['OAuth', 'Basic Auth', 'API Key']
    }
  },
  required: ['flow_name']
};

const uischema = {
  type: 'Categorization',
  elements: [
    {
      type: 'Category',
      label: 'Flow Information',
      elements: [
        {
          type: "HorizontalLayout",
          elements: [{
            "type": "Control",
            "scope": "#/properties/flow_name"
          }]
        }
      ]
    }, {
      type: 'Category',
      label: 'Trigger Information',
      elements: [{
        type: 'Control',
        scope: '#/properties/app_name'
      },
      {
        type: 'Control',
        scope: '#/properties/app_type'
      }, {
        type: 'Control',
        scope: '#/properties/connection_type',
        rule: {
          effect: "SHOW",
          condition: {
            scope: "#/properties/app_type",
            schema: {
              const: 'HTTP'
            }
          }
        }
      }]
    }
  ],
  options: {
    variant: "stepper",
    // showNavButtons: true
  }
};

export default function JsonSchemaForm() {
  const [data, setData] = useState({});
  // setData({name:'vipin',age:24});
  return (
    <div className="bg-white p-4 rounded shadow-md">
      <JsonForms
        schema={schema}
        uischema={uischema}
        data={data}
        renderers={materialRenderers}
        cells={materialCells}
        onChange={({ errors, data }) => setData(data)}
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