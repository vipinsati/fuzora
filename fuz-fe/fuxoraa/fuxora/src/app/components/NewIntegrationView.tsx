import JSONSchemaForm from '../foms/page'
export default function NewIntegrationView() {
    return (
      <div>
        <h2 className="text-2xl font-semibold mb-4">New Integrations</h2>
        <p>Here you will see your new integration forms.</p>
        <JSONSchemaForm />
      </div>
    );
  }