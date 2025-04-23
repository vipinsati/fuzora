// export default function LoginPage() {
//     return (
//       <main className="flex h-screen items-center justify-center bg-gray-100">
//         <div className="bg-white p-6 rounded-xl shadow-md w-full max-w-md">
//           <h1 className="text-2xl font-bold mb-4">Login</h1>
//           <form className="space-y-4">
//             <input type="email" placeholder="Email" className="w-full px-4 py-2 border rounded" />
//             <input type="password" placeholder="Password" className="w-full px-4 py-2 border rounded" />
//             <button className="w-full bg-blue-600 text-white py-2 rounded">Login</button>
//           </form>
//         </div>
//       </main>
//     );
//   }

'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    const res = await fetch('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
      headers: { 'Content-Type': 'application/json' },
    });

    setLoading(false);
    const data = await res.json();
    if (res.ok) {
       const userId = data.userId;
      router.push(`/dashboard/${userId}`);
    } else {
      const data = await res.json();
      setError(data.message || 'Login failed');
    }
  };

  return (
    <main className="flex h-screen items-center justify-center bg-gray-100">
      <form onSubmit={handleLogin} className="bg-white p-6 rounded-lg shadow-md w-full max-w-md space-y-4">
        <h1 className="text-xl font-bold">Login</h1>

        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Email Address"
          className="w-full px-4 py-2 border rounded"
          required
        />

        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          className="w-full px-4 py-2 border rounded"
          required
        />

        {error && <p className="text-red-500 text-sm">{error}</p>}

        <button 
          type="submit" 
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
          disabled={loading}
          >
          {loading ? 'Logging in...' : 'Login'}
        </button>

        <div className="flex justify-between text-sm">
          <Link href="/forgot-password" className="hover:underline text-blue-600 mt-2">Forgot Password?</Link>
          <Link href="/register" className="hover:underline text-blue-600 mt-2">Register</Link>
        </div>
      </form>
    </main>
  );
}