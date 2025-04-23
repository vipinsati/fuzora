// import  JSONSchemaForm from '../foms/page'
// export default function DashboardPage() {
//     return (
//         <main className="h-screen flex items-center justify-center bg-white font-bold">
//             {/* <h1 className="text-2xl font-bold">Welcome to the Dashboard 🎉</h1> */}
//             <JSONSchemaForm />
//         </main>
//     );
// }

'use client';

import { useState } from 'react';
import JSONSchemaForm from '../../foms/page';
import Image from 'next/image';

type Section = 'forms' | 'integrations' | 'settings';
function Logo() {
    return (
        <div className="text-white font-bold text-xl">
            {/* Replace with <Image src="/logo.png" ... /> if you have a logo file */}
            Fuzora
        </div>
    );
}

function ProfileMenu() {
    const [open, setOpen,] = useState(false);


    return (
        <div className="relative">
            <button
                onClick={() => setOpen(!open)}
                className="flex items-center space-x-2 text-white focus:outline-none cursor-pointer"
            >
                <Image
                    src="/profile.jpg" // Replace with actual profile image path
                    alt="Profile"
                    width={32}
                    height={32}
                    className="rounded-full"
                />
                <span className="text-sm font-medium">John Doe</span>
            </button>

            {open && (
                <div className="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded-md z-50 cursor-pointer">
                    <ul className="text-sm text-gray-700">
                        <li
                            className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                            onClick={() => alert('Settings View')}
                        >
                            Settings
                        </li>
                        <li
                            className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                            onClick={() => alert('Profile View')}
                        >
                            Profile
                        </li>
                        <li
                            className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-red-600"
                            onClick={() => alert('Signing out...')}
                        >
                            Sign out
                        </li>
                    </ul>
                </div>
            )}
        </div>
    );
}

export default function DashboardPage() {
    const [showForm, setShowForm,] = useState(false);
    const [section, setSection] = useState<Section>('forms');

    const renderSection = () => {
        switch (section) {
          case 'forms':
            return <FormsView />;
          case 'integrations':
            return <IntegrationsView />;
          case 'settings':
            return <SettingsView />;
          default:
            return null;
        }
      };

    return (
        <div className="h-screen flex flex-col">
            {/* Header */}
            <header className="h-16 bg-gray-800 text-white flex items-center justify-between px-6 shadow-md">
                <Logo />
                <ProfileMenu />
            </header>

            <div className="flex flex-1 overflow-hidden">
                {/* Sidebar */}
                <aside className="w-64 bg-gray-100 border-r p-4 space-y-4">
                    {/* <div className="text-sm font-semibold text-gray-700">Navigation</div> */}
                    <ul className="space-y-5">
                        <li className="hover:text-gray-800 hover:bg-gray-200 cursor-pointer p-3 m-0">New Integration</li>
                        <li className="hover:text-gray-800 hover:bg-gray-200 cursor-pointer p-3 m-0">Integrations</li>
                        <li className="hover:text-gray-800 hover:bg-gray-200 cursor-pointer p-3 m-0">Integration Settings</li>
                    </ul>
                </aside>

                {/* Main Content */}
                <main className="flex-1 overflow-auto p-6 bg-white">
                    <button
                        onClick={() => setShowForm(true)}
                        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                    >
                        Load JSON Form
                    </button>

                    {
                        showForm && (
                            <div className="mt-6">
                                <JSONSchemaForm />
                            </div>
                        )
                    }
                </main>
            </div>
        </div>
    );
}