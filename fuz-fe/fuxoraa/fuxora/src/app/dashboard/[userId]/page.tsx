'use client';

import { useState } from 'react';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import IconButton from '@mui/material/IconButton';
// import Avatar from '../../components/Avatar';
import Avatar from '@mui/material/Avatar'
// import JSONSchemaForm from '../../foms/page';
// import Image from 'next/image';

import NewIntegrationView from '../../components/NewIntegrationView';
import IntegrationsView from '../../components/IntegrationsView';
import SettingsView from '../../components/SettingsView';
import { yellow } from '@mui/material/colors';

// type Section = 'forms' | 'integrations' | 'settings';
type View = 'new' | 'saved' | 'settings';
function Logo() {
    return (
        <div className="text-white font-bold text-xl">
            {/* Replace with <Image src="/logo.png" ... /> if you have a logo file */}
            Fuzora
        </div>
    );
}

// function ProfileMenu() {
//     const [open, setOpen,] = useState(false);
//     const menuRef = useRef<HTMLDivElement>(null);

//     useEffect(() => {
//         function handleClickOutside(event: MouseEvent) {
//             if (menuRef.current && !menuRef.current.contains(event.target as Node)) {
//                 setOpen(false);
//             }
//         }
//         document.addEventListener('mousedown', handleClickOutside);
//         return () => {
//             document.removeEventListener('mousedown', handleClickOutside);
//         };
//     }, []);

//     return (
//         <div className="relative">
//             <button
//                 onClick={() => setOpen(!open)}
//                 className="flex items-center space-x-2 text-white focus:outline-none cursor-pointer"
//             >
//                 {/* <Image
//                     src="/profile.jpg" // Replace with actual profile image path
//                     alt="Profile"
//                     width={32}
//                     height={32}
//                     className="rounded-full"
//                 /> */}
//                 <Avatar name='Vipin Sati' />
//                 <span className="text-sm font-medium">John Doe</span>
//             </button>

//             {open && (
//                 <div className="absolute right-0 mt-2 w-48 bg-white shadow-lg rounded-md z-50 cursor-pointer">
//                     <ul className="text-sm text-gray-700">
//                         <li
//                             className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
//                             onClick={() => alert('Settings View')}
//                         >
//                             Settings
//                         </li>
//                         <li
//                             className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
//                             onClick={() => alert('Profile View')}
//                         >
//                             Profile
//                         </li>
//                         <li
//                             className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-red-600"
//                             onClick={() => alert('Signing out...')}
//                         >
//                             Sign out
//                         </li>
//                     </ul>
//                 </div>
//             )}
//         </div>
//     );
// }

function ProfileMenu() {
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <div>
            <IconButton onClick={handleClick} size="small" sx={{ ml: 2 }}>
                <Avatar sx={{bgcolor:yellow[800]}}>VS</Avatar>
                <span className="ml-2 text-sm font-medium text-white hidden sm:inline">John Doe</span>
            </IconButton>
            <Menu
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                onClick={handleClose}
                PaperProps={{
                    elevation: 2,
                    sx: {
                        mt: 1.5,
                        minWidth: 180,
                    },
                }}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
            >
                <MenuItem onClick={() => alert('Settings View')}>Settings</MenuItem>
                <MenuItem onClick={() => alert('Profile View')}>Profile</MenuItem>
                <MenuItem onClick={() => alert('Signing out...')} sx={{ color: 'red' }}>
                    Sign out
                </MenuItem>
            </Menu>
        </div>
    );
}

export default function DashboardPage() {
    // const [showForm, setShowForm,] = useState(false);
    const [activeView, setActiveView] = useState<View>('new');
    const [collapsed, setCollapsed] = useState(false);

    const renderContent = () => {
        console.log(activeView)
        switch (activeView) {
            case 'new':
                return <NewIntegrationView />;
            case 'saved':
                return <IntegrationsView />;
            case 'settings':
                return <SettingsView />;
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
                <aside className={`bg-gray-100 transition-all duration-300 ${collapsed ? 'w-16' : 'w-64'} p-4`}>
                    <button onClick={() => setCollapsed(!collapsed)} className="mb-4">
                        {collapsed ? '➡️' : '⬅️ Collapse'}
                    </button>

                    <nav className="space-y-2">
                        <button onClick={() => setActiveView('new')} className="block w-full text-left hover:font-semibold">
                            🆕 {!collapsed && 'New Integration'}
                        </button>
                        <button onClick={() => setActiveView('saved')} className="block w-full text-left hover:font-semibold">
                            📁 {!collapsed && 'Integrations'}
                        </button>
                        <button onClick={() => setActiveView('settings')} className="block w-full text-left hover:font-semibold">
                            ⚙️ {!collapsed && 'Settings'}
                        </button>
                    </nav>
                </aside>

                {/* Main Content */}
                {/* <main className="flex-1 overflow-auto p-6 bg-white">
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
                </main> */}
                <main className="flex-1 p-6 overflow-auto bg-white">
                    {/* <header className="mb-4 border-b pb-2 flex justify-between items-center"> */}
                        {/* <h1 className="text-xl font-bold">Dashboard</h1> */}
                        {/* <div className="text-sm text-gray-500">User: {userId}</div> */}
                    {/* </header> */}
                    {renderContent()}
                </main>
            </div>
        </div>
    );
}