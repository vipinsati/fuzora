'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function RegisterPage() {
  const router = useRouter();
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (password !== confirmPassword) {
      return setError('Passwords do not match');
    }

    const res = await fetch('/api/auth/register', {
      method: 'POST',
      body: JSON.stringify({ name, email, password }),
      headers: { 'Content-Type': 'application/json' },
    });

    if (res.ok) {
      setSuccess(true);
      setTimeout(() => {
        router.push('/login');
      }, 2000); // 2-second delay
    } else {
      const data = await res.json();
      setError(data.message || 'Registration failed');
    }
  };

  return (
    <main className="flex h-screen items-center justify-center bg-gray-100">
      <div className="w-full max-w-md">
        {success && (
          <div className="mb-4 p-3 rounded bg-green-100 text-green-800 text-sm text-center">
            🎉 Registration successful! Redirecting to login...
          </div>
        )}
        <form
          onSubmit={handleRegister}
          className="bg-white p-6 rounded-lg shadow-md space-y-4"
        >
          <h1 className="text-xl font-bold">Register</h1>

          <input
            type="text"
            placeholder="Full Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full px-4 py-2 border rounded"
            required
          />

          <input
            type="email"
            placeholder="Email Address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full px-4 py-2 border rounded"
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full px-4 py-2 border rounded"
            required
          />

          <input
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            className="w-full px-4 py-2 border rounded"
            required
          />

          {error && <p className="text-red-500 text-sm">{error}</p>}

          <button
            type="submit"
            className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
          >
            Create Account
          </button>
        </form>
      </div>
    </main>
  );
}