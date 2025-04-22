import { NextRequest, NextResponse } from 'next/server';

export function middleware(request: NextRequest) {
  const session = request.cookies.get('session')?.value;

  const isAuth = !!session;
  const isOnLoginPage = request.nextUrl.pathname === '/login';

  if (!isAuth && !isOnLoginPage) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  if (isAuth && isOnLoginPage) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

// Apply to these paths
export const config = {
  matcher: ['/dashboard', '/login'],
};