import  JSONSchemaForm from '../foms/page'
export default function DashboardPage() {
    return (
        <main className="h-screen flex items-center justify-center bg-white font-bold">
            {/* <h1 className="text-2xl font-bold">Welcome to the Dashboard 🎉</h1> */}
            <JSONSchemaForm />
        </main>
    );
}